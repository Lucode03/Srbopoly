package com.example.srbopoly.data.repository

import android.util.Log
import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.di.ApplicationScope
import com.example.srbopoly.network.NetworkConfig
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResumeGameRepository @Inject constructor(
    private val networkConfig: NetworkConfig,
    private val tokenProvider: AuthTokenProvider,
    @ApplicationScope private val appScope: CoroutineScope
) {
    private var hubConnection: HubConnection? = null
    private var currentGameId: String? = null
    private var intentionalDisconnect = false
    private var reconnectJob: Job? = null

    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECTED)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    private val _voteState = MutableStateFlow<Pair<Int, Int>?>(null)
    val voteState: StateFlow<Pair<Int, Int>?> = _voteState.asStateFlow()

    private val _gameResumedEvent = MutableStateFlow<String?>(null)
    val gameResumedEvent: StateFlow<String?> = _gameResumedEvent.asStateFlow()

    fun connectAndJoinLobby(gameId: String) {
        reconnectJob?.cancel()
        reconnectJob = null

        if (hubConnection?.connectionState == HubConnectionState.CONNECTED) {
            hubConnection?.stop()
        }

        intentionalDisconnect = false
        currentGameId = gameId

        _voteState.value = null
        _gameResumedEvent.value = null

        buildAndStartConnection(attempt = 0)
    }

    private fun buildAndStartConnection(attempt: Int) {
        val gameId = currentGameId ?: return

        hubConnection = HubConnectionBuilder
            .create("${networkConfig.baseUrlGameServer}gamehub")
            .withAccessTokenProvider(Single.defer { Single.just(tokenProvider.token ?: "") })
            .build()

        hubConnection?.on("ResumeVoteUpdated", { totalCount: Int, votedCount: Int ->
            _voteState.value = Pair(totalCount, votedCount)
        }, Int::class.javaObjectType, Int::class.javaObjectType) // javaObjectType je sigurniji za boxing/unboxing Int-a

        hubConnection?.on("GameResumed", { resumedGameId ->
            _gameResumedEvent.value = resumedGameId
        }, String::class.java)

        hubConnection?.onClosed { _ ->
            if (!intentionalDisconnect) {
                _connectionStatus.value = ConnectionStatus.RECONNECTING
                scheduleReconnect(attempt = 0)
            }
        }

        try {
            hubConnection?.start()?.blockingAwait()
            _connectionStatus.value = ConnectionStatus.CONNECTED

            hubConnection?.send("JoinResumeLobby", gameId)
        } catch (e: Exception) {
            Log.w("ResumeGameRepository", "Neuspešno povezivanje za lobi", e)
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

    fun voteToResume() {
        currentGameId?.let { gameId ->
            hubConnection?.send("VoteToResume", gameId)
        }
    }

    fun disconnect() {
        reconnectJob?.cancel()
        reconnectJob = null
        intentionalDisconnect = true
        currentGameId = null
        hubConnection?.stop()
        _connectionStatus.value = ConnectionStatus.DISCONNECTED
    }
}