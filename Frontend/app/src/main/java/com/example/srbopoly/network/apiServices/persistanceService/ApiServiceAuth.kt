package com.example.srbopoly.network.apiServices.persistanceService

import com.example.srbopoly.data.User
import com.example.srbopoly.data.dto.CreateUserRequest
import com.example.srbopoly.data.dto.LoginUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceAuth {
    @POST("User/CreateUser")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): Response<User>

    @POST("User/Login")
    suspend fun login(
        @Body request: LoginUserRequest
    ): Response<User>
}