package com.example.srbopoly.network.apiServices.lobbyService

import com.example.srbopoly.data.Lobby
import com.example.srbopoly.data.LobbyPlayer
import com.example.srbopoly.data.dto.CreateLobbyRequest
import com.example.srbopoly.data.dto.JoinLobbyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServiceLobby {
    @POST("Lobby/CreateLobby")
    suspend fun createLobby(@Body request: CreateLobbyRequest): Response<Lobby>

    @GET("Lobby/GetById/{id}")
    suspend fun getLobbyById(@Path("id") id: Int): Response<Lobby>

    @GET("Lobby/GetByAccessCode/{accessCode}")
    suspend fun getLobbyByAccessCode(@Path("accessCode") accessCode: String): Response<Lobby>

    @GET("Lobby/GetAll")
    suspend fun getAllLobbies(): Response<List<Lobby>>

    @DELETE("Lobby/Delete/{accessCode}")
    suspend fun deleteLobby(@Path("accessCode") accessCode: String): Response<String>


    @POST("LobbyPlayer/Join")
    suspend fun joinLobby(@Body request: JoinLobbyRequest): Response<LobbyPlayer>

    @DELETE("LobbyPlayer/Leave/{accessCode}/{userId}")
    suspend fun leaveLobby(
        @Path("accessCode") accessCode: String,
        @Path("userId") userId: Int
    ): Response<String>

    @GET("LobbyPlayer/GetById/{id}")
    suspend fun getPlayerById(@Path("id") id: Int): Response<LobbyPlayer>
}