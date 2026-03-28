package com.example.srbopoly.data

data class LobbyPlayer (
    val id: Int,
    val userId: Int,
    val username: String,
    val lobbyId: Int,
    val color: Int,
    val rolledNumber: Int,
    val isReady: Boolean
)