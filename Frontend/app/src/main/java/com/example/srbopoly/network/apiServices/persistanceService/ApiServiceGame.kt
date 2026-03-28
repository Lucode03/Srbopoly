package com.example.srbopoly.network.apiServices.persistanceService

import com.example.srbopoly.data.JoinGameRequest
import com.example.srbopoly.data.PlayerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceGame {
    @POST("Player/JoinGame")
    suspend fun joinGame(@Body request: JoinGameRequest):
            Response<PlayerResponse>
}