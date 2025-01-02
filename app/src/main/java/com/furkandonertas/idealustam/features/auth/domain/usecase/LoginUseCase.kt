package com.furkandonertas.idealustam.features.auth.domain.usecase

import com.furkandonertas.idealustam.features.auth.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email boş olamaz"))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException("Şifre boş olamaz"))
        }
        return repository.login(email, password)
    }
} 