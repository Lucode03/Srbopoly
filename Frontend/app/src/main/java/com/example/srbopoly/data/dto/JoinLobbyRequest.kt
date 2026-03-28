package com.example.srbopoly.data.dto

data class JoinLobbyRequest (
    val accessCode: String,
    val userId: Int,
    val username: String?
)