package com.example.srbopoly.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.srbopoly.classes.GameState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.Player
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.factories.FieldFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
//    private val lobbyRepository: LobbyRepository
): ViewModel() {

    val board = List(40) { index ->
        FieldFactory.createField(index)
    }

    private val _dice1 = MutableStateFlow(0)
    val dice1 = _dice1.asStateFlow()

    private val _dice2 = MutableStateFlow(0)
    val dice2 = _dice2.asStateFlow()

    private val _players = mutableStateOf(
        listOf(
            Player(1,"Igrac 1",1000, 10,"Crvena"),
            Player(2,"Igrac 2",1000,20, "Plava"),
            Player(3,"Igrac 3",1000,20, "Bela"),
            Player(4,"Igrac 4",1000,10, "Zelena")
        )
    )
    val players: State<List<Player>> = _players

    private val _gameState = mutableStateOf(GameState(50))
    val gameState: State<GameState> = _gameState

    private val _activeField = mutableStateOf<Field?>(null)
    val activeField: State<Field?> = _activeField

    private fun rollDice():Int {
        val r1 = (1..6).random()
        val r2 = (1..6).random()

        _dice1.value = r1
        _dice2.value = r2

        return dice1.value + dice2.value
    }
    fun getCurrentPlayerId():Int
    {
        val currentIndex = gameState.value.currentPlayer
        val currentPlayer = _players.value[currentIndex]

        return currentPlayer.id
    }
    fun movePlayer() {
        val steps = rollDice()

        val currentIndex = gameState.value.currentPlayer
        val currentPlayer = _players.value[currentIndex]

        val newPosition = (currentPlayer.Position + steps) % board.size

        val updatedPlayer = currentPlayer.copy(
            Position = newPosition
        )

        val updatedList = _players.value.toMutableList()
        updatedList[currentIndex] = updatedPlayer

        _players.value = updatedList

        val field = board[newPosition]

        _activeField.value = field
    }
    private fun nextMove(){
        _dice1.value = 0
        _dice2.value = 0

        _gameState.value.currentPlayer=(_gameState.value.currentPlayer+1)%_players.value.size
        if(_gameState.value.currentPlayer==0)
            _gameState.value.currentMove+=1
    }
    fun applyFieldAction(apply:Boolean=true)
    {
        if(!apply && _activeField.value is PropertyField)
            return
        val currentIndex = gameState.value.currentPlayer
        val currentPlayer = _players.value[currentIndex]

        _activeField.value!!.Action(currentPlayer)
    }
    fun clearActiveField() {
        viewModelScope.launch {
            _activeField.value = null
            delay(1000L)

            nextMove()
        }
    }
}