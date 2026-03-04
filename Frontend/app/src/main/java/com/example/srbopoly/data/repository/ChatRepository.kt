package com.example.srbopoly.data.repository

import com.example.srbopoly.data.SendChatMessageRequest
import com.example.srbopoly.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun sendMessage(
        gameId: Int,
        username: String,
        text: String
    ): Result<Unit> {
        return try {
            val response = api.sendChatMessage(
                gameId,
                SendChatMessageRequest(username, text)
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Greška pri slanju poruke"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}