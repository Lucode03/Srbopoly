package com.example.srbopoly.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.AuthTokenProvider
import com.example.srbopoly.data.Lobby
import com.example.srbopoly.data.repository.LobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val lobbyRepository: LobbyRepository,
    private val tokenProvider: AuthTokenProvider
) : ViewModel() {
    val currentLobby: StateFlow<Lobby?> = lobbyRepository.lobbyState

    val gameStarted: StateFlow<String?> = lobbyRepository.gameStarted

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _maxMovesText = MutableStateFlow(50)
    val maxMovesText = _maxMovesText.asStateFlow()


    private val _dice1 = MutableStateFlow(0)
    val dice1 = _dice1.asStateFlow()

    private val _dice2 = MutableStateFlow(0)
    val dice2 = _dice2.asStateFlow()

    fun setMaxMoves(accessCode: String, userId: Int, moves: Int) {
        lobbyRepository.setMaxPlayCount(accessCode, userId, moves)
    }

    fun setMaxMovesText(moves: Int) {
        _maxMovesText.value = moves
    }

    fun initLobby(accessCode: String, userId: Int) {
        Log.d("LobbyViewModel", "Moj id-je $userId")
        val token = tokenProvider.token ?: return
        viewModelScope.launch(Dispatchers.IO) {
            lobbyRepository.connectToHub(accessCode, userId, token)
        }
    }

    fun setPlayerColor(accessCode: String, userId: Int, colorInt: Int) {
        lobbyRepository.changeColor(accessCode, userId, colorInt)
    }

    fun toggleReady(accessCode: String, userId: Int) {
        lobbyRepository.toggleReady(accessCode, userId)
    }

    fun rollDice(accessCode: String, userId: Int) {
        val r1 = (1..6).random()
        val r2 = (1..6).random()

        _dice1.value = r1
        _dice2.value = r2
        val rolledNumber = dice1.value + dice2.value
        lobbyRepository.rollDice(accessCode, userId, rolledNumber)
    }

    fun leaveLobby(accessCode: String, userId: Int) {
        viewModelScope.launch {
            lobbyRepository.leaveLobby(accessCode, userId)
            lobbyRepository.disconnect()
        }
    }

    fun startGame(accessCode: String, userId: Int) {
        lobbyRepository.startGame(accessCode, userId)
    }

    override fun onCleared() {
        super.onCleared()
        lobbyRepository.disconnect()
    }
}