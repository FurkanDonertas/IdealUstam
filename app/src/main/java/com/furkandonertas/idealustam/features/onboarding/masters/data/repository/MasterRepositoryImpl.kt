package com.furkandonertas.idealustam.features.onboarding.masters.data.repository

import com.furkandonertas.idealustam.features.onboarding.masters.data.datasource.remote.MasterApiService
import com.furkandonertas.idealustam.features.onboarding.masters.domain.model.Master
import com.furkandonertas.idealustam.features.onboarding.masters.domain.repository.MasterRepository
import javax.inject.Inject

class MasterRepositoryImpl @Inject constructor(
    private val masterApiService: MasterApiService
) : MasterRepository {

    override suspend fun getMasters(): Result<List<Master>> {
        return try {
            val response = masterApiService.getMasters()
            if (response.isSuccessful) {
                Result.success(response.body()?.map { masterResponse ->
                    masterResponse.toDomain()
                } ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch masters"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMasterById(id: String): Result<Master> {
        return try {
            val response = masterApiService.getMasterById(id)
            if (response.isSuccessful) {
                response.body()?.let { masterResponse ->
                    Result.success(masterResponse.toDomain())
                } ?: Result.failure(Exception("Master not found"))
            } else {
                Result.failure(Exception("Failed to fetch master"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMasters(query: String): Result<List<Master>> {
        return try {
            val response = masterApiService.searchMasters(query)
            if (response.isSuccessful) {
                Result.success(response.body()?.map { masterResponse ->
                    masterResponse.toDomain()
                } ?: emptyList())
            } else {
                Result.failure(Exception("Failed to search masters"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMastersBySpecialty(specialty: String): Result<List<Master>> {
        return try {
            val response = masterApiService.getMastersBySpecialty(specialty)
            if (response.isSuccessful) {
                Result.success(response.body()?.map { masterResponse ->
                    masterResponse.toDomain()
                } ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch masters by specialty"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 