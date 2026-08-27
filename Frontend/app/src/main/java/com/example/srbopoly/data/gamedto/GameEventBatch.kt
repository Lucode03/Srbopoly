package com.example.srbopoly.data.gamedto

import com.google.gson.JsonElement

data class GameEventBatchRaw(
    val gameId: String,
    val version: Long,
    val events: List<JsonElement>
)