package com.furkandonertas.idealustam.features.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> = _signUpState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun signUp(email: String, password: String, confirmPassword: String) {
        _signUpState.value = SignUpState.Loading

        viewModelScope.launch {
            signUpUseCase(email, password, confirmPassword)
                .onSuccess {
                    _signUpState.value = SignUpState.Success
                }
                .onFailure { exception ->
                    _signUpState.value = SignUpState.Error
                    _errorMessage.value = exception.message ?: "Kayıt başarısız"
                }
        }
    }

    sealed class SignUpState {
        object Initial : SignUpState()
        object Loading : SignUpState()
        object Success : SignUpState()
        object Error : SignUpState()
    }
} 