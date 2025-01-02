package com.furkandonertas.idealustam.features.onboarding.masters.domain.repository

import com.furkandonertas.idealustam.features.onboarding.masters.domain.model.Master

interface MasterRepository {
    suspend fun getMasters(): Result<List<Master>>
    suspend fun getMasterById(id: String): Result<Master>
    suspend fun searchMasters(query: String): Result<List<Master>>
    suspend fun getMastersBySpecialty(specialty: String): Result<List<Master>>
} 