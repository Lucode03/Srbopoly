package com.example.srbopoly.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.srbopoly.classes.GameState
import androidx.compose.runtime.State
import com.example.srbopoly.classes.PlayerState

class GameViewModel : ViewModel() {

    private val _players = mutableStateOf(
        listOf(
            PlayerState(1, "Crvena"),
            PlayerState(2, "Plava",10, isReady = true),
            PlayerState(3, "Bela",12, isReady = true),
            PlayerState(4, "Zelena", isReady = true, isHost = true)
        )
    )
    val players: State<List<PlayerState>> = _players

    private val _gameState = mutableStateOf(GameState(null,50,null))
    val gameState: State<GameState> = _gameState

    fun setPlayerColor(playerId: Int, color: String) {
        val isTaken = _players.value.any {
            it.color == color && it.id != playerId
        }

        if (!isTaken) {
            _players.value = _players.value.map {
                if (it.id == playerId) it.copy(color = color) else it
            }
        }
    }


    fun setMaxMoves(moves: Int) {
        _gameState.value = _gameState.value.copy(maxMoves = moves)
    }

    fun rollDice(id:Int) {
        _players.value = _players.value.map {
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
        return _players.value.any {
            it.color == color && it.id != currentPlayerId
        }
    }

    fun toggleReady(playerId: Int) {
        _players.value = _players.value.map {
            if (it.id == playerId) it.copy(isReady = !it.isReady)
            else it
        }
    }

    fun resetGame() {
        _players.value = listOf(
            PlayerState(1, "Crvena", isHost = true),
            PlayerState(2, "Plava"),
            PlayerState(3, "Crna"),
            PlayerState(4, "Zelena")
        )

        _gameState.value = GameState(
            players = _players.value,
            maxMoves = 50,
            currentPlayerId = null
        )
    }

    fun areAllPlayersReady(): Boolean {
        return _players.value.all { it.isReady }
    }

    fun getPlayersOrdered(): List<PlayerState> {
        return _players.value.sortedByDescending { it.diceResult }
    }
}