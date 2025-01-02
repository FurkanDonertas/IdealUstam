package com.furkandonertas.idealustam.features.home.domain.usecase

import com.furkandonertas.idealustam.features.home.domain.model.Car
import com.furkandonertas.idealustam.features.home.domain.repository.CarRepository

class GetUserCarsUseCase(private val repository: CarRepository) {
    suspend operator fun invoke(): Result<List<Car>> {
        return repository.getUserCars()
    }
} 