package com.furkandonertas.idealustam.features.home.domain.repository

import com.furkandonertas.idealustam.features.home.domain.model.Car

interface CarRepository {
    suspend fun getUserCars(): Result<List<Car>>
    suspend fun addCar(car: Car): Result<Unit>
    suspend fun updateCar(car: Car): Result<Unit>
    suspend fun deleteCar(carId: Int): Result<Unit>
} 