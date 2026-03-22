package com.example.srbopoly.data

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("maxTurns") val maxTurns: Int,
    @SerializedName("currentTurn") val currentTurn: Int,
    @SerializedName("currentPlayerIndex") val currentPlayerIndex: Int,
    @SerializedName(value="accessCode") val accessCode: String
)