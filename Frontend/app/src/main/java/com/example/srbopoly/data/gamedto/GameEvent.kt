package com.example.srbopoly.data.gamedto

sealed interface GameEvent

data class DiceRolledEvent(val playerId: Int, val die1: Int, val die2: Int) : GameEvent
data class PlayerMovedEvent(val playerId: Int, val newPosition: Int) : GameEvent
data class MoneyTransferEvent(val fromId: Int, val toId: Int, val amount: Int, val reason: MoneyTransferReason) : GameEvent
data class PlayerBankruptEvent(val playerId: Int) : GameEvent
data class PlayerSentToJailEvent(val playerId: Int) : GameEvent
data class PlayerReleasedFromJailEvent(val playerId: Int) : GameEvent
data class GetOutOfJailFreeCardUsedEvent(val playerId: Int, val deckType: CardDeckType) : GameEvent
data class PropertyBoughtEvent(val fieldId: Int, val ownerId: Int, val pricePaid: Int) : GameEvent
data class PropertyPurchaseOfferedEvent(val propertyId: Int, val propertyPrice: Int) : GameEvent
data class PropertyPurchaseDeclinedEvent(val propertyId: Int) : GameEvent
data class CardDrawnEvent(val cardId: Int, val deckType: CardDeckType) : GameEvent
data class HouseBuiltEvent(val fieldId: Int, val newHouseCount: Int) : GameEvent
data class HouseSoldEvent(val fieldId: Int, val newHouseCount: Int) : GameEvent
data class PropertyMortgagedEvent(val fieldId: Int) : GameEvent
data class PropertyUnmortgagedEvent(val fieldId: Int) : GameEvent
data class HousesRemovedEvent(val propertyId: Int) : GameEvent
data class PropertyReturnedToBankEvent(val propertyId: Int) : GameEvent
data class TurnEndedEvent(val playerId: Int) : GameEvent
data class PlayerTurnStartedEvent(val playerId: Int) : GameEvent
data class GameEndedEvent(val winnerPlayerId: Int, val reason: GameEndReason) : GameEvent
data class TradeProposedEvent(val tradeId: String, val proposerId: Int, val recipientId: Int) : GameEvent
data class TradeAcceptedEvent(val tradeId: String) : GameEvent
data class TradeRejectedEvent(val tradeId: String) : GameEvent