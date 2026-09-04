package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.dto.GameSummaryDto
import com.example.srbopoly.data.repository.ResumeGameRepository
import com.example.srbopoly.network.apiServices.persistanceService.ApiServiceMyGames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(
    private val apiService: ApiServiceMyGames,
    private val resumeRepository: ResumeGameRepository
) : ViewModel() {

    private val _savedGames = MutableStateFlow<List<GameSummaryDto>>(emptyList())
    val savedGames: StateFlow<List<GameSummaryDto>> = _savedGames.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val voteState = resumeRepository.voteState
    val gameResumedEvent = resumeRepository.gameResumedEvent
    val connectionStatus = resumeRepository.connectionStatus

    init {
        fetchMyGames()
    }

    fun fetchMyGames() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getMyGames()
                if (response.isSuccessful) {
                    _savedGames.value = response.body() ?: emptyList()
                } else {

                }
            } catch (e: Exception) {

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun joinLobbyForGame(gameId: String) {
        resumeRepository.connectAndJoinLobby(gameId)
    }

    fun voteToResume() {
        resumeRepository.voteToResume()
    }

    fun leaveLobby() {
        resumeRepository.disconnect()
    }

    override fun onCleared() {
        super.onCleared()
        resumeRepository.disconnect()
    }
}