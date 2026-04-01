package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

class BonusField(
    Name: String,
    FieldType: FieldType,
    val Bonus: Int
) : Field(Name,FieldType) {

    override fun Action(player: Player,game: Game?) {
        player.Receive(Bonus)
    }
}