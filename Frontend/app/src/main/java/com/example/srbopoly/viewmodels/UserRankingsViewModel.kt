package com.example.srbopoly.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.User
import com.example.srbopoly.data.repository.UserRangListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRankingsViewModel @Inject constructor(
    private val userRangListRepository: UserRangListRepository
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private var isLoading = false

    fun loadNextUsers() {

        if (isLoading)
            return

        viewModelScope.launch {
            try {
                isLoading = true

                val newUsers = userRangListRepository.getNextUsers()

                _users.value += newUsers

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            userRangListRepository.resetPaging()
            _users.value = emptyList()
            loadNextUsers()
        }
    }
}