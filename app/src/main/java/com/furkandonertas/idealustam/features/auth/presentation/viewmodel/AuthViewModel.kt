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

    private val _verificationState = MutableStateFlow<VerificationState>(VerificationState.Initial)
    val verificationState: StateFlow<VerificationState> = _verificationState.asStateFlow()

    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        data class Success(val message: String? = null) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    sealed class VerificationState {
        object Initial : VerificationState()
        object Loading : VerificationState()
        data class Success(val message: String? = null) : VerificationState()
        data class Error(val message: String) : VerificationState()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                authRepository.login(email, password)
                    .onSuccess { response ->
                        _loginState.value = LoginState.Success("Giriş başarılı")
                    }
                    .onFailure { error ->
                        _loginState.value = LoginState.Error(error.message ?: "Giriş başarısız")
                    }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }

    fun register(email: String, password: String, username: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                authRepository.register(email, password, username)
                    .onSuccess { user ->
                        _loginState.value = LoginState.Success("Kayıt başarılı")
                    }
                    .onFailure { error ->
                        _loginState.value = LoginState.Error(error.message ?: "Kayıt başarısız")
                    }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }

    fun verifyEmail(email: String, code: String) {
        viewModelScope.launch {
            _verificationState.value = VerificationState.Loading
            try {
                authRepository.verifyEmail(email, code)
                    .onSuccess { message ->
                        _verificationState.value = VerificationState.Success(message)
                    }
                    .onFailure { error ->
                        _verificationState.value = VerificationState.Error(error.message ?: "Doğrulama başarısız")
                    }
            } catch (e: Exception) {
                _verificationState.value = VerificationState.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }

    fun resendVerificationCode(email: String) {
        viewModelScope.launch {
            _verificationState.value = VerificationState.Loading
            try {
                authRepository.resendVerificationCode(email)
                    .onSuccess { message ->
                        _verificationState.value = VerificationState.Success(message)
                    }
                    .onFailure { error ->
                        _verificationState.value = VerificationState.Error(error.message ?: "Kod gönderilemedi")
                    }
            } catch (e: Exception) {
                _verificationState.value = VerificationState.Error(e.message ?: "Bir hata oluştu")
            }
        }
    }
}