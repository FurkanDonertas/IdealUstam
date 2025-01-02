package com.furkandonertas.idealustam.features.auth.domain.usecase

import com.furkandonertas.idealustam.features.auth.domain.repository.AuthRepository

class ForgotPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email boş olamaz"))
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.failure(IllegalArgumentException("Geçerli bir email adresi giriniz"))
        }
        return repository.forgotPassword(email)
    }
} 