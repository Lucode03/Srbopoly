package com.example.srbopoly.data.gamedto


import com.google.gson.*
import java.lang.reflect.Type

class GameEventDeserializer : JsonDeserializer<GameEvent> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): GameEvent {
        val obj = json.asJsonObject
        val type = obj.get("eventType")?.asString
            ?: throw JsonParseException("Event nema 'eventType' polje: $json")

        val targetClass = when (type) {
            "DiceRolledEvent" -> DiceRolledEvent::class.java
            "PlayerMovedEvent" -> PlayerMovedEvent::class.java
            "MoneyTransferEvent" -> MoneyTransferEvent::class.java
            "PlayerBankruptEvent" -> PlayerBankruptEvent::class.java
            "PlayerSentToJailEvent" -> PlayerSentToJailEvent::class.java
            "PlayerReleasedFromJailEvent" -> PlayerReleasedFromJailEvent::class.java
            "GetOutOfJailFreeCardUsedEvent" -> GetOutOfJailFreeCardUsedEvent::class.java
            "PropertyBoughtEvent" -> PropertyBoughtEvent::class.java
            "PropertyPurchaseOfferedEvent" -> PropertyPurchaseOfferedEvent::class.java
            "PropertyPurchaseDeclinedEvent" -> PropertyPurchaseDeclinedEvent::class.java
            "CardDrawnEvent" -> CardDrawnEvent::class.java
            "HouseBuiltEvent" -> HouseBuiltEvent::class.java
            "HouseSoldEvent" -> HouseSoldEvent::class.java
            "PropertyMortgagedEvent" -> PropertyMortgagedEvent::class.java
            "PropertyUnmortgagedEvent" -> PropertyUnmortgagedEvent::class.java
            "HousesRemovedEvent" -> HousesRemovedEvent::class.java
            "PropertyReturnedToBankEvent" -> PropertyReturnedToBankEvent::class.java
            "TurnEndedEvent" -> TurnEndedEvent::class.java
            "PlayerTurnStartedEvent" -> PlayerTurnStartedEvent::class.java
            "GameEndedEvent" -> GameEndedEvent::class.java
            "TradeProposedEvent" -> TradeProposedEvent::class.java
            "TradeAcceptedEvent" -> TradeAcceptedEvent::class.java
            "TradeRejectedEvent" -> TradeRejectedEvent::class.java
            else -> throw JsonParseException("Nepoznat eventType: $type")
        }

        return context.deserialize(json, targetClass)
    }
}

object GameEventGson {
    val instance: Gson = GsonBuilder()
        .registerTypeAdapter(GameEvent::class.java, GameEventDeserializer())
        .create()
}