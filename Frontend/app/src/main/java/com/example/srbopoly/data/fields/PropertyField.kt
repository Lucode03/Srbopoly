package com.example.srbopoly.data.fields

import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType

class PropertyField(
    Name:String,
    FieldType:FieldType,
    var Price: Int,
    var BaseRent: Int,
    var ownerId: Int? = null,
    var ownerName: String? = null,
    var houseCount: Int = 0,
    var isMortgaged: Boolean = false
):Field(Name,FieldType) {
    override fun Action(player: Player, game: Game?): String = ""
}