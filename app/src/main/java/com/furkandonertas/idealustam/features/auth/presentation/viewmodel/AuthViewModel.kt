package com.furkandonertas.idealustam.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                authRepository.login(email, password)
                    .onSuccess {
                        _loginState.value = LoginState.Success
                    }
                    .onFailure {
                        _loginState.value = LoginState.Error(it.message ?: "Giriş başarısız")
                    }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Bir hata oluştu: ${e.message}")
            }
        }
    }

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            authRepository.register(email, password, username)
                .onSuccess {
                    _loginState.value = LoginState.Success
                }
                .onFailure {
                    _loginState.value = LoginState.Error(it.message ?: "Kayıt başarısız")
                }
        }
    }
} 