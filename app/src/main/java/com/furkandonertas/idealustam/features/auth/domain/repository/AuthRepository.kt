package com.furkandonertas.idealustam.features.auth.domain.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun forgotPassword(email: String): Result<Unit>
    suspend fun logout(): Result<Unit>
} 