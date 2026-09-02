package com.example.srbopoly.data.repository

import android.util.Log
import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.data.gamedto.ChatMessageDto
import com.example.srbopoly.data.gamedto.CommandResultDto
import com.example.srbopoly.data.gamedto.GameCommand
import com.example.srbopoly.data.gamedto.GameEvent
import com.example.srbopoly.data.gamedto.GameEventBatchRaw
import com.example.srbopoly.data.gamedto.GameEventGson
import com.example.srbopoly.data.gamedto.GameStateSnapshotDto
import com.example.srbopoly.di.ApplicationScope
import com.example.srbopoly.network.NetworkConfig
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

enum class ConnectionStatus { CONNECTED, RECONNECTING, DISCONNECTED }

sealed interface GameServerMessage {
    data class Snapshot(val dto: GameStateSnapshotDto) : GameServerMessage
    data class EventsBatch(val events: List<GameEvent>) : GameServerMessage
}

@Singleton
class GameHubRepository @Inject constructor(
    private val networkConfig: NetworkConfig,
    private val tokenProvider: AuthTokenProvider,
    @ApplicationScope private val appScope: CoroutineScope
)  {
    private var hubConnection: HubConnection? = null
    private var currentGameId: String? = null
    private var intentionalDisconnect = false
    private var reconnectJob: Job? = null

    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECTED)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    private val _messages = MutableSharedFlow<GameServerMessage>(extraBufferCapacity = 32)
    val messages: SharedFlow<GameServerMessage> = _messages.asSharedFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessageDto>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessageDto>> = _chatMessages.asStateFlow()

    private val _pauseVotes = MutableStateFlow<List<Int>>(emptyList())
    val pauseVotes: StateFlow<List<Int>> = _pauseVotes.asStateFlow()

    private val _gamePaused = MutableStateFlow(false)
    val gamePaused: StateFlow<Boolean> = _gamePaused.asStateFlow()

    fun connectAndJoin(gameId: String) {
        reconnectJob?.cancel()
        reconnectJob = null

        if (hubConnection?.connectionState == HubConnectionState.CONNECTED) {
            hubConnection?.stop()
        }

        intentionalDisconnect = false
        currentGameId = gameId
        buildAndStartConnection(attempt = 0)
    }

    private fun buildAndStartConnection(attempt: Int) {
        val gameId = currentGameId ?: return

        hubConnection = HubConnectionBuilder
            .create("${networkConfig.baseUrlGameServer}gamehub")
            .withAccessTokenProvider(Single.defer { Single.just(tokenProvider.token ?: "") })
            .build()

        hubConnection?.on("GameSnapshot", { snapshot ->
            _messages.tryEmit(GameServerMessage.Snapshot(snapshot))
        }, GameStateSnapshotDto::class.java)

        hubConnection?.on("GameEvents", { batch ->
            val parsed = batch.events.mapNotNull { rawEvent ->
                try {
                    GameEventGson.instance.fromJson(rawEvent, GameEvent::class.java)
                } catch (e: Exception) {
                    Log.e("GameHubRepository", "Neprepoznat event: $rawEvent", e)
                    null
                }
            }
            _messages.tryEmit(GameServerMessage.EventsBatch(parsed))
        }, GameEventBatchRaw::class.java)

        hubConnection?.on("PauseVoteUpdated", { votedPlayerIds ->
            _pauseVotes.value = votedPlayerIds.toList()
        }, IntArray::class.java)

        hubConnection?.on("GamePaused") {
            _gamePaused.value = true
        }

        hubConnection?.on("ChatMessageReceived", { message ->
            _chatMessages.value = _chatMessages.value + message
        }, ChatMessageDto::class.java)

        hubConnection?.onClosed { _ ->
            if (!intentionalDisconnect) {
                Log.w("GameHubRepository", "Konekcija prekinuta neočekivano, pokušavam reconnect")
                _connectionStatus.value = ConnectionStatus.RECONNECTING
                scheduleReconnect(attempt = 0)
            }
        }

        try {
            hubConnection?.start()?.blockingAwait()
            _connectionStatus.value = ConnectionStatus.CONNECTED
            hubConnection?.send("JoinGame", gameId)
        } catch (e: Exception) {
            Log.w("GameHubRepository", "Neuspešno povezivanje (pokušaj $attempt)", e)
            _connectionStatus.value = ConnectionStatus.RECONNECTING
            scheduleReconnect(attempt + 1)
        }
    }

    private fun scheduleReconnect(attempt: Int) {
        reconnectJob = appScope.launch {
            val delayMs = minOf(2000L * (attempt + 1), 15_000L)
            delay(delayMs)
            if (!intentionalDisconnect && currentGameId != null) {
                buildAndStartConnection(attempt + 1)
            }
        }
    }

    suspend fun sendCommand(command: GameCommand): CommandResultDto = withContext(Dispatchers.IO) {
        hubConnection
            ?.invoke(CommandResultDto::class.java, "SendCommand", command)
            ?.blockingGet()
            ?: CommandResultDto(success = false, error = "Nema aktivne konekcije")
    }

    fun requestPause() {
        hubConnection?.send("RequestPause")
    }

    fun sendChatMessage(text: String) {
        hubConnection?.send("SendChatMessage", text)
    }

    fun disconnect() {
        reconnectJob?.cancel()
        reconnectJob = null

        intentionalDisconnect = true
        currentGameId = null
        hubConnection?.stop()
        _connectionStatus.value = ConnectionStatus.DISCONNECTED
        _chatMessages.value = emptyList()
    }
}