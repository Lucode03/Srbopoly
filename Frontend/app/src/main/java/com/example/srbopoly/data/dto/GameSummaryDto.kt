package com.example.srbopoly.data.dto

data class GameSummaryDto(
    val gameId: String,
    val playerNames: List<String>,
    val savedAtUtc: String
)