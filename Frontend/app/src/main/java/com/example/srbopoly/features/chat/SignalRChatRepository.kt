package com.example.srbopoly.features.chat

import android.util.Log
import com.example.srbopoly.data.ChatUiModel
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton
import com.example.srbopoly.network.NetworkConfig
import com.microsoft.signalr.HubConnectionState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.future.await

@Singleton
class SignalRChatRepository @Inject constructor(
    private val networkConfig: NetworkConfig
) {

    private val _incomingMessages = MutableSharedFlow<ChatUiModel>(
        replay = 10,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val incomingMessages = _incomingMessages.asSharedFlow()

    private lateinit var hubConnection: HubConnection


    fun startConnection(onConnected: () -> Unit = {}) {
        val hubUrl = "${networkConfig.baseUrl}chathub"
        Log.d("SignalR", "Povezivanje na: $hubUrl")
        hubConnection = HubConnectionBuilder.create(hubUrl).build()

        hubConnection.on("ReceiveMessage", { message ->
            Log.d("SignalR", "Primljeno: $message")
            val chatMessage = message as Map<*, *>
            Log.d("SignalR", "Map keys: ${chatMessage.keys}")
            val username = chatMessage["username"] as? String ?: ""
            val text = chatMessage["text"] as? String ?: ""
            Log.d("SignalR", "Parsirano: username='$username', text='$text'")

            val emitted = _incomingMessages.tryEmit(ChatUiModel(username, text))
            if (!emitted)
                Log.e("SignalR", "tryEmit nije uspeo - nema aktivnih kolektora!")
            }, Map::class.java)

        hubConnection.start().subscribe(
            {
                println("SignalR connected")
                onConnected()
            },
            { error ->
                error.printStackTrace()
                println("SignalR failed to start: ${error.message}")
            }
        )
    }

    fun joinGameGroup(gameId: Int) {
        if (hubConnection.connectionState != HubConnectionState.CONNECTED) {
            println("SignalR not connected yet, delaying joinGameGroup")
            return
        }
        hubConnection.send("JoinGameGroup", gameId)
    }

    fun leaveGameGroup(gameId: Int) {
        hubConnection.send("LeaveGameGroup", gameId)
    }

    fun stopConnection() {
        hubConnection.stop()
    }
}