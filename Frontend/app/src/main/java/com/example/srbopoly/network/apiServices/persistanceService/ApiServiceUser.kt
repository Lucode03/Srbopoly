package com.example.srbopoly.network.apiServices.persistanceService

import com.example.srbopoly.data.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceUser {
    @GET("User/GetUsersPaged")
    suspend fun getUsersPaged(
        @Query("start") start: Int,
        @Query("count") count: Int
    ): Response<List<User>>
}