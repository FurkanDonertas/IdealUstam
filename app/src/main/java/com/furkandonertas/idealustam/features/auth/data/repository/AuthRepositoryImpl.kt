package com.furkandonertas.idealustam.features.auth.data.repository

import com.furkandonertas.idealustam.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        // TODO: Backend entegrasyonu yapılacak
        return Result.success(Unit)
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        // TODO: Backend entegrasyonu yapılacak
        return Result.success(Unit)
    }

    override suspend fun forgotPassword(email: String): Result<Unit> {
        // TODO: Backend entegrasyonu yapılacak
        return Result.success(Unit)
    }

    override suspend fun logout(): Result<Unit> {
        // TODO: Backend entegrasyonu yapılacak
        return Result.success(Unit)
    }
} 