package com.furkandonertas.idealustam.features.settings.domain.usecase

import com.furkandonertas.idealustam.features.settings.domain.model.UserSettings
import com.furkandonertas.idealustam.features.settings.domain.repository.SettingsRepository

class GetUserSettingsUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(): Result<UserSettings> {
        return repository.getUserSettings()
    }
} 