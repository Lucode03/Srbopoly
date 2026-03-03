package com.example.srbopoly.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _joinGameIdInput = MutableStateFlow("")
    val joinGameIdInput = _joinGameIdInput.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun onJoinGameIdChange(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            _joinGameIdInput.value = newValue
        }
    }

    fun fetchUserGames(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = gameRepository.getGamesForUser(userId)

            result.onSuccess { gamesList ->
                _games.value = gamesList
            }.onFailure { exception ->
                _error.value = exception.message
            }

            _isLoading.value = false
        }
    }

    fun createNewGame(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val gameResult = gameRepository.createGame(maxTurns = 20)

            gameResult.onSuccess { createdGame ->
                val playerResult = gameRepository.addPlayerToGame(userId, createdGame.id)

                playerResult.onSuccess {
                    fetchUserGames(userId)
                }.onFailure {
                    _error.value = "Igra kreirana, ali ne i igrač: ${it.message}"
                }
            }.onFailure {
                _error.value = "Neuspešno kreiranje igre: ${it.message}"
            }

            _isLoading.value = false
        }
    }

    fun joinExistingGame(userId: Int) {
        val gameId = _joinGameIdInput.value.toIntOrNull()
        if (gameId == null) {
            _error.value = "Unesite ispravan ID igre"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = gameRepository.addPlayerToGame(userId, gameId)

            result.onSuccess {
                _joinGameIdInput.value = ""
                fetchUserGames(userId)
            }.onFailure {
                _error.value = "Neuspešno pridruživanje: ${it.message}"
            }

            _isLoading.value = false
        }
    }
}