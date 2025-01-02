package com.furkandonertas.idealustam.features.services.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.services.domain.model.Service
import com.furkandonertas.idealustam.features.services.domain.usecase.GetServicesUseCase
import kotlinx.coroutines.launch

class ServicesViewModel(private val getServicesUseCase: GetServicesUseCase) : ViewModel() {

    private val _servicesState = MutableLiveData<ServicesState>()
    val servicesState: LiveData<ServicesState> = _servicesState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadServices()
    }

    fun loadServices() {
        _servicesState.value = ServicesState.Loading

        viewModelScope.launch {
            getServicesUseCase()
                .onSuccess { services ->
                    _servicesState.value = if (services.isEmpty()) {
                        ServicesState.Empty
                    } else {
                        ServicesState.Success(services)
                    }
                }
                .onFailure { exception ->
                    _servicesState.value = ServicesState.Error
                    _errorMessage.value = exception.message ?: "Servisler yüklenirken bir hata oluştu"
                }
        }
    }

    sealed class ServicesState {
        object Initial : ServicesState()
        object Loading : ServicesState()
        object Empty : ServicesState()
        data class Success(val services: List<Service>) : ServicesState()
        object Error : ServicesState()
    }
} 