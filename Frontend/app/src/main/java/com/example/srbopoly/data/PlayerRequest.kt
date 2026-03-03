package com.example.srbopoly.data

data class PlayerRequest(
    val userId: Int,
    val gameId: Int,
    val balance: Int = 10000,
    val position: Int = 0,
    val color: Int = 0,
    val isInJail: Boolean = false
)
