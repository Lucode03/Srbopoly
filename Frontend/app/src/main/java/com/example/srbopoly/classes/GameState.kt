package com.example.srbopoly.classes

data class GameState(
    val maxMoves: Int,
    var currentPlayer: Int=0,
    var currentMove:Int=1
)