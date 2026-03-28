package com.example.srbopoly.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.srbopoly.classes.GameState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.classes.PlayerState
import com.example.srbopoly.data.Player
import com.example.srbopoly.data.repository.LobbyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val lobbyRepository: LobbyRepository
): ViewModel() {

    private val _playersSettings = mutableStateOf(
        listOf(
            PlayerState(1, "Crvena"),
            PlayerState(2, "Plava",10, isReady = true),
            PlayerState(3, "Bela",12, isReady = true),
            PlayerState(4, "Zelena", isReady = true, isHost = true)
        )
    )
    val playersSettings: State<List<PlayerState>> = _playersSettings

    private val _players = mutableStateOf(
        listOf(
            Player("Igrac 1",1000, 10,"Crvena"),
            Player("Igrac 2",1000,22, "Plava"),
            Player("Igrac 3",1000,12, "Bela"),
            Player("Igrac 4",1000,14, "Zelena")
        )
    )
    val players: State<List<Player>> = _players

    private val _gameState = mutableStateOf(GameState(null,50,null))
    val gameState: State<GameState> = _gameState

    fun setPlayerColor(playerId: Int, color: String) {
        val isTaken = _playersSettings.value.any {
            it.color == color && it.id != playerId
        }

        if (!isTaken) {
            _playersSettings.value = _playersSettings.value.map {
                if (it.id == playerId) it.copy(color = color) else it
            }
        }
    }


    fun setMaxMoves(moves: Int) {
        _gameState.value = _gameState.value.copy(maxMoves = moves)
    }

    fun rollDice(id:Int) {
        _playersSettings.value = _playersSettings.value.map {
            val dice1 = (1..6).random()
            val dice2 = (1..6).random()

            if (it.id == id) it.copy(
                    dice1 = dice1,
                    dice2 = dice2,
                    diceResult = dice1 + dice2
                )
            else it

        }
    }

    fun isColorTaken(color: String, currentPlayerId: Int): Boolean {
        return _playersSettings.value.any {
            it.color == color && it.id != currentPlayerId
        }
    }

    fun toggleReady(playerId: Int) {
        _playersSettings.value = _playersSettings.value.map {
            if (it.id == playerId) it.copy(isReady = !it.isReady)
            else it
        }
    }

    fun resetGameSettings() {
        _playersSettings.value = listOf(
            PlayerState(1, "Crvena", isHost = true),
            PlayerState(2, "Plava"),
            PlayerState(3, "Crna"),
            PlayerState(4, "Zelena")
        )

        _gameState.value = GameState(
            players = _playersSettings.value,
            maxMoves = 50,
            currentPlayerId = null
        )
    }

    fun leaveLobby(accessCode: String, userId: Int) {
        viewModelScope.launch {
            val result = lobbyRepository.leaveLobby(accessCode, userId)

            result.onSuccess {
                Log.d("GameRepository","Uspesno je napustio igru $accessCode igrac: $userId")
            }.onFailure { exception ->

            }
        }
    }

    fun areAllPlayersReady(): Boolean {
        return _playersSettings.value.all { it.isReady }
    }

    fun getPlayersOrdered(): List<PlayerState> {
        return _playersSettings.value.sortedByDescending { it.diceResult }
    }
}