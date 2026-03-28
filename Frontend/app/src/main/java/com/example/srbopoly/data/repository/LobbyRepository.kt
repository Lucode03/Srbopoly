package com.example.srbopoly.data.repository

import com.example.srbopoly.data.Lobby
import com.example.srbopoly.data.LobbyPlayer
import com.example.srbopoly.data.dto.CreateLobbyRequest
import com.example.srbopoly.data.dto.JoinLobbyRequest
import com.example.srbopoly.network.apiServices.lobbyService.ApiServiceLobby
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRepository @Inject constructor(
    private val lobbyApi: ApiServiceLobby
){
    suspend fun createLobby(hostUserId: Int, hostUsername: String): Result<Lobby> {
        return try {
            val response = lobbyApi.createLobby(CreateLobbyRequest(hostUserId, hostUsername))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Greška pri kreiranju lobija: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun joinLobby(accessCode: String, userId: Int, username: String): Result<LobbyPlayer> {
        return try {
            val response = lobbyApi.joinLobby(JoinLobbyRequest(accessCode, userId, username))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Greška pri ulasku u lobi"
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun leaveLobby(accessCode: String, userId: Int): Result<String> {
        return try {
            val response = lobbyApi.leaveLobby(accessCode, userId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: "Uspešno napušten lobi")
            } else {
                Result.failure(Exception("Greška pri napuštanju lobija"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLobbyByCode(accessCode: String): Result<Lobby> {
        return try {
            val response = lobbyApi.getLobbyByAccessCode(accessCode)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Lobi nije pronađen"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}