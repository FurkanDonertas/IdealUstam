package com.furkandonertas.idealustam.features.services.domain.usecase

import com.furkandonertas.idealustam.features.services.domain.model.Service
import com.furkandonertas.idealustam.features.services.domain.repository.ServiceRepository

class GetServicesUseCase(private val repository: ServiceRepository) {
    suspend operator fun invoke(): Result<List<Service>> {
        return repository.getServices()
    }
} 