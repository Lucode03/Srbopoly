package com.example.srbopoly.data.repository

import com.example.srbopoly.data.Lobby
import com.example.srbopoly.data.LobbyPlayer
import com.example.srbopoly.data.dto.CreateLobbyRequest
import com.example.srbopoly.data.dto.JoinLobbyRequest
import com.example.srbopoly.network.NetworkConfig
import com.example.srbopoly.network.apiServices.lobbyService.ApiServiceLobby
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRepository @Inject constructor(
    private val lobbyApi: ApiServiceLobby,
    private val networkConfig: NetworkConfig
){
    private var hubConnection: HubConnection? = null

    private val _lobbyState = MutableStateFlow<Lobby?>(null)
    val lobbyState: StateFlow<Lobby?> = _lobbyState.asStateFlow()

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
            hubConnection?.send("LeaveLobbyGroup", accessCode)
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

    fun connectToHub(accessCode: String) {
        if (hubConnection?.connectionState == HubConnectionState.CONNECTED) return

        hubConnection = HubConnectionBuilder
            .create("${networkConfig.baseUrlLobbyServer}lobbyHub")
            .build()

        hubConnection?.on("LobbyStateUpdated", { lobby ->
            _lobbyState.value = lobby
        }, Lobby::class.java)

        hubConnection?.start()?.blockingAwait()
        hubConnection?.send("JoinLobbyGroup", accessCode)
    }

    fun disconnect() {
        hubConnection?.stop()
        _lobbyState.value = null
    }

    fun changeColor(accessCode: String, userId: Int, colorInt: Int) {
        hubConnection?.send("ChangeColor", accessCode, userId, colorInt)
    }

    fun toggleReady(accessCode: String, userId: Int) {
        hubConnection?.send("ToggleReady", accessCode, userId)
    }

    fun rollDice(accessCode: String, userId: Int, rolledNumber: Int) {
        hubConnection?.send("RollDice", accessCode, userId, rolledNumber)
    }

}