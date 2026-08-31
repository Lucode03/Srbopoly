package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.gamedto.CardDeckType
import com.example.srbopoly.data.gamedto.CardDrawnEvent
import com.example.srbopoly.data.gamedto.DiceRolledEvent
import com.example.srbopoly.data.gamedto.GameCommand
import com.example.srbopoly.data.gamedto.GameEndedEvent
import com.example.srbopoly.data.gamedto.GameEvent
import com.example.srbopoly.data.gamedto.GameStateSnapshotDto
import com.example.srbopoly.data.gamedto.GetOutOfJailFreeCardUsedEvent
import com.example.srbopoly.data.gamedto.HouseBuiltEvent
import com.example.srbopoly.data.gamedto.HouseSoldEvent
import com.example.srbopoly.data.gamedto.MoneyTransferEvent
import com.example.srbopoly.data.gamedto.PlayerBankruptEvent
import com.example.srbopoly.data.gamedto.PlayerMovedEvent
import com.example.srbopoly.data.gamedto.PlayerReleasedFromJailEvent
import com.example.srbopoly.data.gamedto.PlayerSentToJailEvent
import com.example.srbopoly.data.gamedto.PropertyBoughtEvent
import com.example.srbopoly.data.gamedto.PropertyMortgagedEvent
import com.example.srbopoly.data.gamedto.PropertyPurchaseDeclinedEvent
import com.example.srbopoly.data.gamedto.PropertyPurchaseOfferedEvent
import com.example.srbopoly.data.gamedto.PropertyUnmortgagedEvent
import com.example.srbopoly.data.gamedto.TradeAcceptedEvent
import com.example.srbopoly.data.gamedto.TradeOfferDto
import com.example.srbopoly.data.gamedto.TradeRejectedEvent
import com.example.srbopoly.data.gamedto.describe
import com.example.srbopoly.data.repository.GameHubRepository
import com.example.srbopoly.data.repository.GameServerMessage
import com.example.srbopoly.factories.CardCatalog
import com.example.srbopoly.factories.CardTextInfo
import com.example.srbopoly.factories.FieldFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TURN_TIMEOUT_SECONDS = 60

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameHubRepository: GameHubRepository
): ViewModel() {

    private val fieldCatalog = List(40) { index -> FieldFactory.createField(index) }
    val board get() = fieldCatalog

    private val _gameState = MutableStateFlow<GameStateSnapshotDto?>(null)
    val gameState: StateFlow<GameStateSnapshotDto?> = _gameState.asStateFlow()


    private val _dice1 = MutableStateFlow(0)
    val dice1 = _dice1.asStateFlow()

    private val _dice2 = MutableStateFlow(0)
    val dice2 = _dice2.asStateFlow()
    private val _diceResult = MutableStateFlow<Int?>(null)
    val diceResult = _diceResult.asStateFlow()

    private val _actionResult = MutableStateFlow<String?>(null)
    val actionResult = _actionResult.asStateFlow()

    private val _pendingPurchaseField = MutableStateFlow<Field?>(null)
    val pendingPurchaseField: StateFlow<Field?> = _pendingPurchaseField.asStateFlow()

    private val _highlightedFields = MutableStateFlow<List<Int>>(emptyList())
    val highlightedFields: StateFlow<List<Int>> = _highlightedFields.asStateFlow()

    private val _lastDrawnCardText = MutableStateFlow<CardTextInfo?>(null)
    val lastDrawnCardText: StateFlow<CardTextInfo?> = _lastDrawnCardText.asStateFlow()

    private val _commandError = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val commandError: SharedFlow<String> = _commandError.asSharedFlow()

    private val _remainingTime = MutableStateFlow(TURN_TIMEOUT_SECONDS)
    val remainingTime: StateFlow<Int> = _remainingTime.asStateFlow()

    private val _gameEndedInfo = MutableStateFlow<GameEndedEvent?>(null)
    val gameEndedInfo: StateFlow<GameEndedEvent?> = _gameEndedInfo.asStateFlow()

    private var timerJob: Job? = null

    val pauseVotes: StateFlow<List<Int>> = gameHubRepository.pauseVotes
    val gamePaused: StateFlow<Boolean> = gameHubRepository.gamePaused

    init {
        viewModelScope.launch {
            gameHubRepository.messages.collect { message ->
                when (message) {
                    is GameServerMessage.EventsBatch -> handleEvents(message.events)
                    is GameServerMessage.Snapshot -> {
                        _gameState.value = message.dto
                        syncFieldState(message.dto)
                        restartCosmeticTimer()
                    }
                }
            }
        }
    }

    private suspend fun animateMovement(from: Int, to: Int) {
        val path = mutableListOf<Int>()
        var current = from
        val boardSize = board.size
        val steps = if (to >= from) to - from else (boardSize - from) + to

        repeat(steps) {
            current = (current + 1) % boardSize
            path.add(current)
            _highlightedFields.value = path.toList()
            delay(300)
        }
        _highlightedFields.value = emptyList()
    }


    private fun restartCosmeticTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            _remainingTime.value = TURN_TIMEOUT_SECONDS
            while (_remainingTime.value > 0) {
                delay(1000)
                _remainingTime.value--
            }
        }
    }

    private suspend fun handleEvents(events: List<GameEvent>) {
        var lastRollWasDiceRoll = false

        for (event in events) {
            when (event) {
                is DiceRolledEvent -> {
                    lastRollWasDiceRoll = true
                    _dice1.value = event.die1
                    _dice2.value = event.die2
                    _diceResult.value = event.die1 + event.die2
                    delay(2000)
                    _diceResult.value = null
                }
                is PlayerMovedEvent -> {
                    val oldPosition = _gameState.value?.players
                        ?.firstOrNull { it.id == event.playerId }?.position ?: event.newPosition
                    if (lastRollWasDiceRoll) {
                        animateMovement(oldPosition, event.newPosition)
                    } else {
                        _highlightedFields.value = emptyList()
                    }
                    lastRollWasDiceRoll = false
                }
                is CardDrawnEvent -> {
                    _lastDrawnCardText.value = CardCatalog.infoFor(event.deckType, event.cardId)
                    delay(3000)
                    _lastDrawnCardText.value = null
                }
                is PropertyPurchaseOfferedEvent -> {
                    _pendingPurchaseField.value = board.getOrNull(event.propertyId)
                }
                is PropertyBoughtEvent -> {
                    showActionResult("${playerName(event.ownerId)} je kupio/la ${fieldName(event.fieldId)} za ${event.pricePaid}")
                }
                is PropertyPurchaseDeclinedEvent -> {
                    showActionResult("Niko nije kupio ${fieldName(event.propertyId)}")
                }
                is HouseBuiltEvent -> {
                    val what = if (event.newHouseCount == 5) "hotel" else "kuća"
                    showActionResult("Izgrađen/a je $what na ${fieldName(event.fieldId)}")
                }
                is HouseSoldEvent -> {
                    showActionResult("Prodata je kuća na ${fieldName(event.fieldId)}")
                }
                is PropertyMortgagedEvent -> {
                    showActionResult("${fieldName(event.fieldId)} je hipotekovano")
                }
                is PropertyUnmortgagedEvent -> {
                    showActionResult("Hipoteka na ${fieldName(event.fieldId)} je otkupljena")
                }
                is PlayerSentToJailEvent -> {
                    showActionResult("${playerName(event.playerId)} je poslat/a u zatvor!")
                }
                is PlayerReleasedFromJailEvent -> {
                    showActionResult("${playerName(event.playerId)} je izašao/la iz zatvora")
                }
                is GetOutOfJailFreeCardUsedEvent -> {
                    showActionResult("${playerName(event.playerId)} je iskoristio/la kartu za izlazak iz zatvora")
                }
                is PlayerBankruptEvent -> {
                    showActionResult("${playerName(event.playerId)} je bankrotirao/la!", durationMs = 3500)
                }
                is TradeAcceptedEvent -> {
                    showActionResult("Razmena je prihvaćena")
                }
                is TradeRejectedEvent -> {
                    showActionResult("Razmena je odbijena")
                }
                is GameEndedEvent -> {
                    _gameEndedInfo.value = event
                }
                is MoneyTransferEvent -> {
                    event.describe(playerName(event.fromId), playerName(event.toId))
                        ?.let { showActionResult(it) }
                }
                else -> Unit
            }
        }
    }

    private fun syncFieldState(state: GameStateSnapshotDto) {
        val fieldStatesById = state.fields.associateBy { it.fieldId }
        val playersById = state.players.associateBy { it.id }

        board.forEach { field ->
            if (field is PropertyField) {
                val fieldState = fieldStatesById[field.GameFieldID] ?: return@forEach
                field.ownerId = fieldState.ownerId
                field.ownerName = fieldState.ownerId?.let { playersById[it]?.name }
                field.houseCount = fieldState.houseCount
                field.isMortgaged = fieldState.isMortgaged
            }
        }
    }

    private fun playerName(id: Int): String =
        _gameState.value?.players?.firstOrNull { it.id == id }?.name ?: "Igrač"

    private fun fieldName(fieldId: Int): String =
        board.getOrNull(fieldId)?.Name ?: "polje"

    private suspend fun showActionResult(text: String, durationMs: Long = 2500) {
        _actionResult.value = text
        delay(durationMs)
        _actionResult.value = null
    }


    fun joinGame(gameId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gameHubRepository.connectAndJoin(gameId)
        }
    }

    fun rollDice() = sendCommand(GameCommand.RollDice())
    fun buyProperty() {
        sendCommand(GameCommand.BuyProperty())
        _pendingPurchaseField.value = null
    }
    fun declineBuy() {
        sendCommand(GameCommand.DeclineBuy())
        _pendingPurchaseField.value = null
    }
    fun endTurn() = sendCommand(GameCommand.EndTurn())
    fun buildHouse(fieldId: Int) = sendCommand(GameCommand.BuildHouse(fieldId))
    fun sellHouse(fieldId: Int) = sendCommand(GameCommand.SellHouse(fieldId))
    fun mortgageProperty(fieldId: Int) = sendCommand(GameCommand.MortgageProperty(fieldId))
    fun unmortgageProperty(fieldId: Int) = sendCommand(GameCommand.UnmortgageProperty(fieldId))
    fun useGetOutOfJailFreeCard(deckType: CardDeckType) =
        sendCommand(GameCommand.UseGetOutOfJailFreeCard(deckType))

    fun proposeTrade(targetPlayerId: Int, offer: TradeOfferDto) =
        sendCommand(GameCommand.ProposeTrade(targetPlayerId, offer))
    fun acceptTrade() = sendCommand(GameCommand.AcceptTrade())
    fun rejectTrade() = sendCommand(GameCommand.RejectTrade())

    fun requestPause() {
        viewModelScope.launch(Dispatchers.IO) {
            gameHubRepository.requestPause()
        }
    }

    private fun sendCommand(command: GameCommand) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = gameHubRepository.sendCommand(command)
            if (!result.success && result.error != null) {
                _commandError.tryEmit(result.error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameHubRepository.disconnect()
    }
}