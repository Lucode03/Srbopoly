package com.example.srbopoly.network.apiServices.persistanceService

import com.example.srbopoly.data.dto.GameSummaryDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceMyGames {
    @GET("api/games/mine")
    suspend fun getMyGames(): Response<List<GameSummaryDto>>
}