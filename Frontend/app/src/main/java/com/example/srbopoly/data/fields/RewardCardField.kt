package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType

class RewardCardField(
    Name: String,
    FieldType:FieldType
):Field(Name,FieldType) {

    override fun Action(player: Player, game: Game?)
    {
//        val card=game!!.DrawRewardsCard()
//        card.Apply(player)
    }
}