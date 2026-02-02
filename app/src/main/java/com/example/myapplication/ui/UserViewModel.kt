package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val loginSuccess: Boolean = false,
    val registerSuccess: Boolean = false
)

class UserViewModel(private val repo: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(UserUiState())
    val state: StateFlow<UserUiState> = _state

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                repo.login(username, password)
                _state.value = _state.value.copy(loading = false, loginSuccess = true)
            } catch (e: java.io.IOException) {
                _state.value = _state.value.copy(loading = false, error = "No internet connection")
            } catch (e: retrofit2.HttpException) {
                _state.value = _state.value.copy(loading = false, error = "Server error: ${e.code()}")
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = "Unknown error: ${e.localizedMessage}")
            }
        }
    }

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                repo.register(username, password)
                _state.value = _state.value.copy(loading = false, registerSuccess = true)
            } catch (e: java.io.IOException) {
                _state.value = _state.value.copy(loading = false, error = "No internet connection")
            } catch (e: retrofit2.HttpException) {
                _state.value = _state.value.copy(loading = false, error = "Server error: ${e.code()}")
            } catch (e: Exception) {
                _state.value = _state.value.copy(loading = false, error = "Unknown error: ${e.localizedMessage}")
            }
        }
    }
}
