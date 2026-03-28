package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

abstract class Field
(
    var Name: String,
    var GameFieldID: Int
)
{
    abstract fun Action(player: Player,game: Game?)
    fun getPosition(): Pair<Int, Int> {
        return when (GameFieldID) {
            in 0..10 -> 0 to GameFieldID                     // gore
            in 11..19 -> (GameFieldID - 10) to 10           // desno
            in 20..30 -> 10 to (30 - GameFieldID)           // dole
            in 31..39 -> (40 - GameFieldID) to 0            // levo
            else -> 0 to 0
        }
    }
}
