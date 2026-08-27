package com.example.srbopoly.data.gamedto

data class GameStateSnapshotDto(
    val version: Long,
    val players: List<PlayerDto>,
    val fields: List<FieldStateDto>,
    val currentPlayerIndex: Int,
    val currentTurn: ActiveTurnStateDto,
    val lastDiceRoll: DiceRollDto?,
    val chanceDeckRemaining: Int,
    val surpriseDeckRemaining: Int
)

data class PlayerDto(
    val id: Int,
    val name: String,
    val position: Int,
    val money: Int,
    val isInJail: Boolean,
    val jailTurnsSpent: Int,
    val isBankrupt: Boolean,
    val consecutiveDoubles: Int,
    val color: GameColor,
    val heldGetOutOfJailCardDecks: List<CardDeckType>
)

data class FieldStateDto(
    val fieldId: Int,
    val ownerId: Int?,
    val isMortgaged: Boolean,
    val houseCount: Int
)

data class ActiveTurnStateDto(
    val playerId: Int,
    val phase: TurnPhase,
    val pendingPurchaseFieldId: Int?,
    val pendingTrade: PendingTradeDto?
)

data class PendingTradeDto(
    val id: String,
    val proposerId: Int,
    val recipientId: Int,
    val offeredPropertyIds: List<Int>,
    val offeredMoney: Int,
    val requestedPropertyIds: List<Int>,
    val requestedMoney: Int
)

data class DiceRollDto(
    val die1: Int,
    val die2: Int,
    val total: Int,
    val isDouble: Boolean
)