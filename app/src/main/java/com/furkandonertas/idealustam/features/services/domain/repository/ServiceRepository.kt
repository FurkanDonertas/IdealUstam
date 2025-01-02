package com.furkandonertas.idealustam.features.services.domain.repository

import com.furkandonertas.idealustam.features.services.domain.model.Service

interface ServiceRepository {
    suspend fun getServices(): Result<List<Service>>
    suspend fun getServiceById(serviceId: Int): Result<Service>
    suspend fun searchServices(query: String): Result<List<Service>>
    suspend fun getServicesByCategory(categoryId: Int): Result<List<Service>>
} 