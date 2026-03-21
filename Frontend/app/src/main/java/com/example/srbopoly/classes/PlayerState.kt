package com.example.srbopoly.classes

import com.example.srbopoly.R

data class PlayerState(
    val id: Int,
    val color: String,
    val diceResult: Int = 0,
    val dice1:Int = 0,
    val dice2:Int = 0,
    val isReady: Boolean = false,
    val isHost: Boolean = false
)
fun getDiceImage(value: Int): Int {
    return when (value) {
        1 -> R.drawable.kocka1
        2 -> R.drawable.kocka2
        3 -> R.drawable.kocka3
        4 -> R.drawable.kocka4
        5 -> R.drawable.kocka5
        6 -> R.drawable.kocka6
        else -> R.drawable.kocka0
    }
}
fun getFigure(color: String): Int {
    return when (color) {
        "Crvena" -> R.drawable.figure_red
        "Plava" -> R.drawable.figure_blue
        "Zelena" -> R.drawable.figure_green
        "Žuta" -> R.drawable.figure_yellow
        "Narandžasta" -> R.drawable.figure_orange
        "Bela" -> R.drawable.figure_white
        else -> R.drawable.figure_white
    }
}