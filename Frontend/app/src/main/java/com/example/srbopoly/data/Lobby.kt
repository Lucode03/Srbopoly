package com.example.srbopoly.data

data class Lobby(
    val id: Int,
    val hostUserId: Int,
    val maxPlayCout: Int,
    val accessCode: String,
    val players: List<LobbyPlayer>
)
