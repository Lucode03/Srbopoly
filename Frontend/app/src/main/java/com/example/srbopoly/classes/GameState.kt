package com.example.srbopoly.classes

data class GameState(
    val players: List<PlayerState>?,
    val maxMoves: Int,
    val currentPlayerId: Int?
)