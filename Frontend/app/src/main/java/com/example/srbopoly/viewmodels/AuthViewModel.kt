package com.example.srbopoly.viewmodels
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srbopoly.data.SessionManager
import com.example.srbopoly.data.User
import com.example.srbopoly.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _usernameLogin = MutableStateFlow("")
    val usernameLogin: StateFlow<String> = _usernameLogin.asStateFlow()

    private val _passwordLogin = MutableStateFlow("")
    val passwordLogin: StateFlow<String> = _passwordLogin.asStateFlow()

    private val _usernameSignup = MutableStateFlow("")
    val usernameSignup: StateFlow<String> = _usernameSignup.asStateFlow()

    private val _passwordSignup = MutableStateFlow("")
    val passwordSignup: StateFlow<String> = _passwordSignup.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val isSessionLoaded = MutableStateFlow(false)

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun onUsernameLoginChange(newUsername: String) {
        _usernameLogin.value = newUsername
    }
    fun onPasswordLoginChange(password: String) {
        _passwordLogin.value = password
    }

    fun onUsernameSignupChange(newUsername: String) {
        _usernameSignup.value = newUsername
    }
    fun onPasswordSignupChange(password: String) {
        _passwordSignup.value = password
    }


    init {
        viewModelScope.launch {
            sessionManager.userFlow.collect { session ->
                _user.value = session?.let {
                    _usernameLogin.value = it.username
                    User(it.id, it.username, it.points)
                }
                isSessionLoaded.value = true
            }
        }
    }

    fun login() {
        val username = _usernameLogin.value.trim()
        val password = _passwordLogin.value
        if (username.isBlank()) {
            _error.value = "Unesite korisničko ime"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.login(username, password)
            _isLoading.value = false

            result.onSuccess { user ->
                _user.value = user
                sessionManager.saveUser(user.id, user.username, user.points)
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }

    fun signup() {
        val username = _usernameSignup.value.trim()
        val password = _passwordSignup.value
        if (username.isBlank()) {
            _error.value = "Unesite korisničko ime"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val result = repository.register(username, password)
            _isLoading.value = false

            result.onSuccess { user ->
                _user.value = user
                _usernameLogin.value = user.username
                sessionManager.saveUser(user.id, user.username, user.points)
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }

    fun signout(){
        viewModelScope.launch {
            sessionManager.clear()
            _user.value = null
        }
    }

    fun clearError() {
        _error.value = null
    }
}