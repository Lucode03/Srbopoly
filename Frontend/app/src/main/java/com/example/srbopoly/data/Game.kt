package com.example.srbopoly.data

import com.example.srbopoly.data.fields.Field
import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("maxTurns") val maxTurns: Int,
    @SerializedName("currentTurn") val currentTurn: Int,
    @SerializedName("currentPlayerIndex") val currentPlayerIndex: Int,
    @SerializedName(value="accessCode") val accessCode: String,
    val GameBoard:List<Field>,
    val RewardCardsDeck: List<RewardCard>,
    val SurpriseCardsDeck: List<SurpriseCard>

)
{
    fun DrawSurpriseCard(): SurpriseCard
    {
        //logika za mesanje spila
        return SurpriseCardsDeck.first()
    }
    fun DrawRewardsCard(): RewardCard
    {
        //logika za mesanje spila
        return RewardCardsDeck.first()
    }
}