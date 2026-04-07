package com.example.srbopoly.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.srbopoly.classes.GameState
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.Player
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.enums.TurnPhase
import com.example.srbopoly.factories.FieldFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
//    private val lobbyRepository: LobbyRepository
): ViewModel() {

    private val _board = List(40) { index ->
        FieldFactory.createField(index)
    }
    val board = _board

//    val rewardCardsDeck = List(40) { mutableStateListOf<RewardCard>() }
//    val surpriseCardsDeck = List(40) { mutableStateListOf<SurpriseCard>() }

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

    //animation variables
    private val _diceResult = MutableStateFlow<Int?>(null)
    val diceResult = _diceResult.asStateFlow()

    private val _activeField = mutableStateOf<Field?>(null)
    val activeField: State<Field?> = _activeField

    private val _highlightedFields = MutableStateFlow<List<Int>>(emptyList())
    val highlightedFields = _highlightedFields

    private var _phase = mutableStateOf(TurnPhase.DICE_ROLL)
    val phase: State<TurnPhase> = _phase

    private var _remainingTime = mutableStateOf(20)
    val remainingTime: State<Int> = _remainingTime

    private val _actionResult = MutableStateFlow<String?>(null)
    val actionResult = _actionResult.asStateFlow()

    private var timerJob: Job? = null

    private fun startTimer(seconds: Int, onFinish: () -> Unit) {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            _remainingTime.value = seconds

            while (_remainingTime.value > 0) {
                delay(1000)
                _remainingTime.value--
            }

            onFinish()
        }
    }

    private fun startDicePhase() {
        _phase.value = TurnPhase.DICE_ROLL
        startTimer(20) {
            movePlayer()
        }
    }
    private fun startPlayingPhase() {
        _phase.value = TurnPhase.PLAYING
        startTimer(120) {
            if(activeField.value!=null)
                clearActiveField()
            nextTurn()
        }
    }
    private fun rollDice():Int {
        val r1 = (1..6).random()
        val r2 = (1..6).random()

        _dice1.value = r1
        _dice2.value = r2

        return dice1.value+dice2.value
    }
    fun getCurrentPlayer():Player
    {
        val currentIndex = gameState.value.currentPlayer
        val currentPlayer = _players.value[currentIndex]

        return currentPlayer
    }
    fun startTurn(){
        startDicePhase()
    }
    fun movePlayer() {
        timerJob?.cancel()

        val steps = rollDice()
        _diceResult.value = steps

        //proveri da li svi igraci vide animaciju
        viewModelScope.launch {
            delay(2000)

            _diceResult.value = null

            val currentPlayer = getCurrentPlayer()

            delay(800)

            val path = mutableListOf<Int>()
            var currentPosition = currentPlayer.Position

            repeat(steps) {
                currentPosition = (currentPosition + 1) % board.size

                path.add(currentPosition)
                _highlightedFields.value = path.toList()

                delay(600)
            }

            currentPlayer.Move(steps)

            _highlightedFields.value = emptyList()
            delay(200)

            val field = board[currentPlayer.Position]
            _activeField.value = field

            startPlayingPhase()

        }

    }
    fun nextTurn(){
        timerJob?.cancel()

        _dice1.value = 0
        _dice2.value = 0

        _gameState.value.currentPlayer=(_gameState.value.currentPlayer+1)%_players.value.size
        if(_gameState.value.currentPlayer==0)
            _gameState.value.currentMove+=1

        startDicePhase()
    }
    fun applyFieldAction(apply:Boolean=true)
    {
        if(!apply && _activeField.value is PropertyField)
            return
        viewModelScope.launch {
            val currentPlayer = getCurrentPlayer()

            _actionResult.value = _activeField.value!!.Action(currentPlayer)

            delay(4000)

            _actionResult.value = null
        }
    }
    fun clearActiveField() {
        viewModelScope.launch {
            _activeField.value = null
            delay(1000L)
        }
    }
}