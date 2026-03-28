package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

abstract class SurpriseCardField(
    Name: String,
    GameFieldID: Int
):Field(Name,GameFieldID) {

    override fun Action(player: Player,game: Game?)
    {
        val card=game!!.DrawSurpriseCard()
        card.Apply(player)
    }
}