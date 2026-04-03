package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.Lobby
import com.example.srbopoly.data.repository.LobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : ViewModel() {
    val currentLobby: StateFlow<Lobby?> = lobbyRepository.lobbyState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _maxMoves = MutableStateFlow(50)
    val maxMoves = _maxMoves.asStateFlow()

    private val _dice1 = MutableStateFlow(0)
    val dice1 = _dice1.asStateFlow()

    private val _dice2 = MutableStateFlow(0)
    val dice2 = _dice2.asStateFlow()

    fun setMaxMoves(moves: Int) {
        _maxMoves.value = moves
    }

    fun initLobby(accessCode: String, userId: Int) {
        lobbyRepository.connectToHub(accessCode, userId)
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

    override fun onCleared() {
        super.onCleared()
        lobbyRepository.disconnect()
    }
}