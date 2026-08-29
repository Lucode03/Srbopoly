package com.example.srbopoly.data.gamedto

fun GameColor.toColorName(): String = when (this) {
    GameColor.RED -> "Crvena"
    GameColor.BLUE -> "Plava"
    GameColor.GREEN -> "Zelena"
    GameColor.YELLOW -> "Žuta"
    GameColor.ORANGE -> "Narandžasta"
    GameColor.WHITE -> "Bela"
}