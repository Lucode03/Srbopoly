package com.example.srbopoly.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.srbopoly.classes.GameState
import androidx.compose.runtime.State
import com.example.srbopoly.data.Player
import com.example.srbopoly.data.repository.LobbyRepository
import com.example.srbopoly.factories.FieldFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _gameState = mutableStateOf(GameState(50,3))
    val gameState: State<GameState> = _gameState


    fun rollDice(id:Int) {
        val r1 = (1..6).random()
        val r2 = (1..6).random()

        _dice1.value = r1
        _dice2.value = r2
        val rolledNumber = dice1.value + dice2.value

        //roll dice
        //change next player
    }
}