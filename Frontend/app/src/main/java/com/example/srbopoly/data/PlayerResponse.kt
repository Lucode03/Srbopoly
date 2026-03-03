package com.example.srbopoly.data

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("gameId") val gameId: Int,
    @SerializedName("balance") val balance: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("userId") val userId: Int
)
