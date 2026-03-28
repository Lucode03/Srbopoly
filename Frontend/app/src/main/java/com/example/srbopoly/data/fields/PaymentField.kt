package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

class PaymentField(
    Name:String,
    GameFieldId:Int,
    var Price:Int
):Field(Name,GameFieldId) {
    override fun Action(player: Player,game: Game?)
    {
        player.Pay(Price)
    }
}