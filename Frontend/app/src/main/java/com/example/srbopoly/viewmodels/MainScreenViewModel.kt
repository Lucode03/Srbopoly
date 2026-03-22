package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _gameCode = MutableStateFlow<String?>(null)
    val gameCode: StateFlow<String?> = _gameCode.asStateFlow()


    private val _joinedGameCode = MutableStateFlow<String?>(null)
    val joinedGameCode: StateFlow<String?> = _joinedGameCode.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun createNewGame(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val gameResult = gameRepository.createGame(maxTurns = 20)
            gameResult.onSuccess { createdGame ->
                _gameCode.value = createdGame.accessCode
                val playerResult = gameRepository.addPlayerToGame(userId, createdGame.id)

                playerResult.onSuccess {
                }.onFailure {
                    _error.value = "Igra kreirana, ali ne i igrač: ${it.message}"
                }
            }.onFailure {
                _error.value = "Neuspešno kreiranje igre: ${it.message}"
            }

            _isLoading.value = false
        }
    }

    fun joinExistingGame(userId: Int, code: String) {
        if (code.isBlank() || code.length < 6) {
            _error.value = "Pristupni kod mora imati 6 karaktera."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = gameRepository.joinGameByCode(userId, code)

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
}