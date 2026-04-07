package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType

class PaymentField(
    Name:String,
    FieldType:FieldType,
    var Price:Int
):Field(Name,FieldType) {
    override fun Action(player: Player,game: Game?)
    {
        player.Pay(Price)
    }
}