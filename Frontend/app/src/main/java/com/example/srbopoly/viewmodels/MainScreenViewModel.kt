package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.repository.LobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : ViewModel() {

    private val _gameCode = MutableStateFlow<String?>(null)
    val gameCode: StateFlow<String?> = _gameCode.asStateFlow()

    private val _joinedGameCode = MutableStateFlow<String?>(null)
    val joinedGameCode: StateFlow<String?> = _joinedGameCode.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun createNewGame(userId: Int, username: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val gameResult = lobbyRepository.createLobby(userId, username)
            gameResult.onSuccess { createdGame ->
                _gameCode.value = createdGame.accessCode
            }.onFailure {
                _error.value = "Neuspešno kreiranje igre: ${it.message}"
            }

            _isLoading.value = false
        }
    }

    fun joinExistingGame(userId: Int, code: String, username: String) {
        if (code.isBlank() || code.length < 6) {
            _error.value = "Pristupni kod mora imati 6 karaktera."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = lobbyRepository.joinLobby(code, userId, username)

            result.onSuccess {
                _error.value = null
                _joinedGameCode.value = code
            }.onFailure {
                _error.value = "Neuspešno pridruživanje: ${it.message}"
            }

            _isLoading.value = false
        }
    }

    fun resetGameCode() {
        _gameCode.value = null
    }

    fun resetJoinedGameCode() {
        _joinedGameCode.value = null
    }
    fun clearError(){
        _error.value=null
    }
}