package com.furkandonertas.idealustam.features.auth.domain.usecase

import com.furkandonertas.idealustam.features.auth.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String, confirmPassword: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email boş olamaz"))
        }
        if (password.isBlank()) {
            return Result.failure(IllegalArgumentException("Şifre boş olamaz"))
        }
        if (password != confirmPassword) {
            return Result.failure(IllegalArgumentException("Şifreler eşleşmiyor"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Şifre en az 6 karakter olmalıdır"))
        }
        return repository.signUp(email, password)
    }
} 