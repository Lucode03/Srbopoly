package com.example.srbopoly.classes

data class GameState(
    val maxMoves: Int,
    val currentPlayerId: Int?,
    val currentMove:Int=0
)