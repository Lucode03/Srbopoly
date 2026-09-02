package com.example.srbopoly.data.gamedto

data class ChatMessageDto(
    val playerId: Int,
    val username: String,
    val text: String,
    val sentAtUtc: String
)