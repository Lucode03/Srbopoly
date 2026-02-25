package com.example.srbopoly.features.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.User
import com.example.srbopoly.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _usernameState = MutableStateFlow("")
    val usernameState: StateFlow<String> = _usernameState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _usernameState.value = newUsername
    }

    fun checkUsername() {
        val username = _usernameState.value.trim()
        if (username.isBlank()) {
            _error.value = "Unesite korisničko ime"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.checkUserExists(username)
            _isLoading.value = false

            result.onSuccess { user ->
                _user.value = user
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}