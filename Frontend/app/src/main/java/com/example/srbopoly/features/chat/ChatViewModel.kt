package com.example.srbopoly.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.ChatUiModel
import com.example.srbopoly.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log;

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val signalRChatRepository: SignalRChatRepository
) : ViewModel() {

    private var currentUsername: String = ""
    private val _messages = MutableStateFlow<List<ChatUiModel>>(emptyList())
    val messages: StateFlow<List<ChatUiModel>> = _messages.asStateFlow()

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            signalRChatRepository.incomingMessages.collect { message ->
                if (message.username != currentUsername) {
                    _messages.value = _messages.value + message
                    Log.d("ChatViewModel", "SignalR poruka stigla od drugog: $message")
                } else {
                    Log.d("ChatViewModel", "Ignorišem sopstvenu poruku sa SignalR")
                }
            }
        }
    }

    fun startChat(gameId: Int, username: String) {
        currentUsername = username
        signalRChatRepository.startConnection {
            signalRChatRepository.joinGameGroup(gameId)
            Log.d("ChatViewModel", "Uspesno ste se pridruzili grupi.")
        }
    }

    fun stopChat(gameId: Int) {
        signalRChatRepository.leaveGameGroup(gameId)
        signalRChatRepository.stopConnection()
    }

    fun onInputChange(newValue: String) {
        _input.value = newValue
    }

    fun sendMessage(gameId: Int, username: String) {
        val text = _input.value.trim()
        if (text.isBlank()) return

        viewModelScope.launch {
            val result = chatRepository.sendMessage(gameId, username, text)

            result.onSuccess {
                _messages.value = _messages.value + ChatUiModel(
                    username = username,
                    text = text
                )
                _input.value = ""
            }.onFailure {
                _error.value = it.message
                _input.value = it.message.toString()
            }
        }
    }
}