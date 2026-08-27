package com.example.srbopoly.data.gamedto

sealed class GameCommand(val commandType: String) {
    open val playerId: Int = 0

    class RollDice : GameCommand("RollDiceCommand")
    class BuyProperty : GameCommand("BuyPropertyCommand")
    class DeclineBuy : GameCommand("DeclineBuyCommand")
    class BuildHouse(val fieldId: Int) : GameCommand("BuildHouseCommand")
    class SellHouse(val fieldId: Int) : GameCommand("SellHouseCommand")
    class MortgageProperty(val fieldId: Int) : GameCommand("MortgagePropertyCommand")
    class UnmortgageProperty(val fieldId: Int) : GameCommand("UnmortgagePropertyCommand")
    class ProposeTrade(val targetPlayerId: Int, val offer: TradeOfferDto) : GameCommand("ProposeTradeCommand")
    class AcceptTrade : GameCommand("AcceptTradeCommand")
    class RejectTrade : GameCommand("RejectTradeCommand")
    class EndTurn : GameCommand("EndTurnCommand")
    class UseGetOutOfJailFreeCard(val deckType: CardDeckType) : GameCommand("UseGetOutOfJailFreeCardCommand")
}

data class TradeOfferDto(
    val offeredPropertyIds: List<Int>,
    val offeredMoney: Int,
    val requestedPropertyIds: List<Int>,
    val requestedMoney: Int
)