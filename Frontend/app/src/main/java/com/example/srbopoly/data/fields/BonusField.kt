package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

class BonusField(
    name: String,
    gameFieldID: Int,
    val bonus: Int
) : Field(name, gameFieldID) {

    override fun Action(player: Player,game: Game?) {
        player.Receive(bonus)
    }
}