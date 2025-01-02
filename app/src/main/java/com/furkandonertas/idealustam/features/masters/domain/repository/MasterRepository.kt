package com.furkandonertas.idealustam.features.masters.domain.repository

import com.furkandonertas.idealustam.features.masters.domain.model.Master

interface MasterRepository {
    suspend fun getMasters(): Result<List<Master>>
    suspend fun getMasterById(masterId: Int): Result<Master>
    suspend fun searchMasters(query: String): Result<List<Master>>
    suspend fun getMastersBySpecialization(specialization: String): Result<List<Master>>
    suspend fun toggleFavoriteMaster(masterId: Int): Result<Unit>
} 