package com.example.srbopoly.data.fields

import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType

class BonusField(
    Name: String,
    FieldType: FieldType,
    val Bonus: Int
) : Field(Name,FieldType) {

    override fun Action(player: Player,game: Game?):String {
        player.Receive(Bonus)
        return "Igrač ${player.Username} je preuzeo bonus od $Bonus \uD83D\uDCB5"
    }
}