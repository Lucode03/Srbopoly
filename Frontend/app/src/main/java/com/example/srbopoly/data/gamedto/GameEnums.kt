package com.example.srbopoly.data.gamedto

import com.google.gson.annotations.SerializedName

enum class TurnPhase {
    @SerializedName("awaitingRoll") AWAITING_ROLL,
    @SerializedName("awaitingPropertyDecision") AWAITING_PROPERTY_DECISION,
    @SerializedName("turnActions") TURN_ACTIONS,
    @SerializedName("turnEnded") TURN_ENDED,
    @SerializedName("gameOver") GAME_OVER
}

enum class CardDeckType {
    @SerializedName("chanseCardDeck") CHANCE,
    @SerializedName("surpriseCardDeck") SURPRISE
}

enum class GameColor {
    @SerializedName("red") RED,
    @SerializedName("blue") BLUE,
    @SerializedName("green") GREEN,
    @SerializedName("yellow") YELLOW,
    @SerializedName("orange") ORANGE,
    @SerializedName("white") WHITE
}

enum class MoneyTransferReason {
    @SerializedName("passedGo") PASSED_GO,
    @SerializedName("taxPayment") TAX_PAYMENT,
    @SerializedName("rentPayment") RENT_PAYMENT,
    @SerializedName("jailFeePayment") JAIL_FEE_PAYMENT,
    @SerializedName("housePurchase") HOUSE_PURCHASE,
    @SerializedName("houseSale") HOUSE_SALE,
    @SerializedName("propertyPurchase") PROPERTY_PURCHASE,
    @SerializedName("mortgageProceeds") MORTGAGE_PROCEEDS,
    @SerializedName("unmortgagePayment") UNMORTGAGE_PAYMENT,
    @SerializedName("tradeSettlement") TRADE_SETTLEMENT,
    @SerializedName("cardBonus") CARD_BONUS,
    @SerializedName("cardPenalty") CARD_PENALTY,
    @SerializedName("cardPlayerTransfer") CARD_PLAYER_TRANSFER,
    @SerializedName("propertyRepairs") PROPERTY_REPAIRS,
    @SerializedName("landingBonus") LANDING_BONUS
}

enum class GameEndReason {
    @SerializedName("lastPlayerStanding") LAST_PLAYER_STANDING,
    @SerializedName("turnLimitReached") TURN_LIMIT_REACHED
}