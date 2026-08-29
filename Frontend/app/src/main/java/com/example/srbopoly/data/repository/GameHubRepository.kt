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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

sealed interface GameServerMessage {
    data class Snapshot(val dto: GameStateSnapshotDto) : GameServerMessage
    data class EventsBatch(val events: List<GameEvent>) : GameServerMessage
}

@Singleton
class GameHubRepository @Inject constructor(
    private val networkConfig: NetworkConfig,
    private val tokenProvider: AuthTokenProvider
)  {
    private var hubConnection: HubConnection? = null

    private val _messages = MutableSharedFlow<GameServerMessage>(extraBufferCapacity = 32)
    val messages: SharedFlow<GameServerMessage> = _messages.asSharedFlow()

    fun connectAndJoin(gameId: String) {
        if (hubConnection?.connectionState == HubConnectionState.CONNECTED) {
            hubConnection?.stop()
        }

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
    }
}