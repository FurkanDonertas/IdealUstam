package com.furkandonertas.idealustam.features.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.auth.domain.usecase.ForgotPasswordUseCase
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val forgotPasswordUseCase: ForgotPasswordUseCase) : ViewModel() {

    private val _forgotPasswordState = MutableLiveData<ForgotPasswordState>()
    val forgotPasswordState: LiveData<ForgotPasswordState> = _forgotPasswordState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun submitEmail(email: String) {
        _forgotPasswordState.value = ForgotPasswordState.Loading

        viewModelScope.launch {
            forgotPasswordUseCase(email)
                .onSuccess {
                    _forgotPasswordState.value = ForgotPasswordState.Success
                }
                .onFailure { exception ->
                    _forgotPasswordState.value = ForgotPasswordState.Error
                    _errorMessage.value = exception.message ?: "Şifre sıfırlama başarısız"
                }
        }
    }

    sealed class ForgotPasswordState {
        object Initial : ForgotPasswordState()
        object Loading : ForgotPasswordState()
        object Success : ForgotPasswordState()
        object Error : ForgotPasswordState()
    }
} 