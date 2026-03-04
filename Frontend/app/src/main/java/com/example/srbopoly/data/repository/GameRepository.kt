package com.example.srbopoly.data.repository

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.PlayerRequest
import com.example.srbopoly.data.PlayerResponse
import com.example.srbopoly.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getGamesForUser(userId: Int): Result<List<Game>> {
        return try {
            val response = api.getGamesByUserId(userId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                if (response.code() == 404) {
                    Result.success(emptyList())
                } else {
                    Result.failure(Exception("Greška na serveru: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createGame(maxTurns: Int): Result<Game> {
        return try {
            val response = api.createGame(maxTurns)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Greška pri kreiranju igre"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun addPlayerToGame(userId: Int, gameId: Int): Result<PlayerResponse> {
        return try {
            val request = PlayerRequest(userId = userId, gameId = gameId)
            val response = api.createPlayer(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Greška pri dodavanju igrača u igru"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

}