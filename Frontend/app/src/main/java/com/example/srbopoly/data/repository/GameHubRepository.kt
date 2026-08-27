package com.example.srbopoly.data.repository

import android.util.Log
import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.data.gamedto.CommandResultDto
import com.example.srbopoly.data.gamedto.GameCommand
import com.example.srbopoly.data.gamedto.GameEvent
import com.example.srbopoly.data.gamedto.GameEventBatchRaw
import com.example.srbopoly.data.gamedto.GameEventGson
import com.example.srbopoly.data.gamedto.GameStateSnapshotDto
import com.example.srbopoly.network.NetworkConfig
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameHubRepository @Inject constructor(
    private val networkConfig: NetworkConfig,
    private val tokenProvider: AuthTokenProvider
)  {
    private var hubConnection: HubConnection? = null

    private val _gameState = MutableStateFlow<GameStateSnapshotDto?>(null)
    val gameState: StateFlow<GameStateSnapshotDto?> = _gameState.asStateFlow()

    private val _events = MutableSharedFlow<GameEvent>(extraBufferCapacity = 16)
    val events: SharedFlow<GameEvent> = _events.asSharedFlow()

    fun connectAndJoin(gameId: String) {
        if (hubConnection?.connectionState == HubConnectionState.CONNECTED) {
            hubConnection?.stop()
        }

        hubConnection = HubConnectionBuilder
            .create("${networkConfig.baseUrlGameServer}gamehub")
            .withAccessTokenProvider(Single.defer { Single.just(tokenProvider.token ?: "") })
            .build()

        hubConnection?.on("GameSnapshot", { snapshot ->
            _gameState.value = snapshot
        }, GameStateSnapshotDto::class.java)

        hubConnection?.on("GameEvents", { batch ->
            batch.events.forEach { rawEvent ->
                try {
                    val event = GameEventGson.instance.fromJson(rawEvent, GameEvent::class.java)
                    _events.tryEmit(event)
                } catch (e: Exception) {
                    Log.e("GameHubRepository", "Neprepoznat event: $rawEvent", e)
                }
            }
        }, GameEventBatchRaw::class.java)

        hubConnection?.start()?.blockingAwait()
        hubConnection?.send("JoinGame", gameId)
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

    fun disconnect() {
        hubConnection?.stop()
        _gameState.value = null
    }
}