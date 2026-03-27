package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

class JailField(
    Name:String,
    GameFieldId:Int
):Field(Name,GameFieldId) {
    override fun Action(player: Player, game: Game?)
    {
        player.IsInJail=true
    }
}