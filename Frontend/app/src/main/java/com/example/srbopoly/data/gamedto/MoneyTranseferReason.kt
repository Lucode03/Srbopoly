package com.example.srbopoly.data.gamedto

fun MoneyTransferEvent.describe(fromName: String, toName: String): String? = when (reason) {
    MoneyTransferReason.PASSED_GO -> "$toName je prošao/la Start i pokupio/la $amount"
    MoneyTransferReason.TAX_PAYMENT -> "$fromName je platio/la porez od $amount"
    MoneyTransferReason.RENT_PAYMENT -> "$fromName je platio/la rentu od $amount igraču $toName"
    MoneyTransferReason.JAIL_FEE_PAYMENT -> "$fromName je platio/la kauciju od $amount"
    MoneyTransferReason.MORTGAGE_PROCEEDS -> "$toName je hipotekovao/la nekretninu i dobio/la $amount"
    MoneyTransferReason.UNMORTGAGE_PAYMENT -> "$fromName je otkupio/la hipoteku za $amount"
    MoneyTransferReason.CARD_BONUS -> "$toName je dobio/la $amount preko kartice"
    MoneyTransferReason.CARD_PENALTY -> "$fromName je platio/la $amount zbog kartice"
    MoneyTransferReason.CARD_PLAYER_TRANSFER -> "$fromName je platio/la $amount igraču $toName"
    MoneyTransferReason.PROPERTY_REPAIRS -> "$fromName je platio/la $amount za opšte popravke"
    MoneyTransferReason.LANDING_BONUS -> "$toName je dobio/la $amount za sletanje tačno na Parking"
    else -> null
}