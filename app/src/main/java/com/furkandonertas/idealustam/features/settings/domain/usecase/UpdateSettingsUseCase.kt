package com.furkandonertas.idealustam.features.settings.domain.usecase

import com.furkandonertas.idealustam.features.settings.domain.model.UserSettings
import com.furkandonertas.idealustam.features.settings.domain.repository.SettingsRepository

class UpdateSettingsUseCase(private val repository: SettingsRepository) {
    suspend fun updateTheme(isDarkTheme: Boolean): Result<Unit> {
        return repository.updateTheme(isDarkTheme)
    }

    suspend fun updateNotifications(isEnabled: Boolean): Result<Unit> {
        return repository.updateNotifications(isEnabled)
    }

    suspend fun logout(): Result<Unit> {
        return repository.logout()
    }
} 