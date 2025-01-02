package com.furkandonertas.idealustam.features.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.home.domain.model.Car
import com.furkandonertas.idealustam.features.home.domain.usecase.GetUserCarsUseCase
import kotlinx.coroutines.launch

class MyCarViewModel(private val getUserCarsUseCase: GetUserCarsUseCase) : ViewModel() {

    private val _carState = MutableLiveData<CarState>()
    val carState: LiveData<CarState> = _carState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadUserCars()
    }

    fun loadUserCars() {
        _carState.value = CarState.Loading

        viewModelScope.launch {
            getUserCarsUseCase()
                .onSuccess { cars ->
                    _carState.value = if (cars.isEmpty()) {
                        CarState.Empty
                    } else {
                        CarState.Success(cars)
                    }
                }
                .onFailure { exception ->
                    _carState.value = CarState.Error
                    _errorMessage.value = exception.message ?: "Araçlar yüklenirken bir hata oluştu"
                }
        }
    }

    sealed class CarState {
        object Initial : CarState()
        object Loading : CarState()
        object Empty : CarState()
        data class Success(val cars: List<Car>) : CarState()
        object Error : CarState()
    }
} 