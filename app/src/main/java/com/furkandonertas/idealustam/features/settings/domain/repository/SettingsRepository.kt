package com.furkandonertas.idealustam.features.settings.domain.repository

import com.furkandonertas.idealustam.features.settings.domain.model.UserSettings

interface SettingsRepository {
    suspend fun getUserSettings(): Result<UserSettings>
    suspend fun updateUserSettings(settings: UserSettings): Result<Unit>
    suspend fun updateTheme(isDarkTheme: Boolean): Result<Unit>
    suspend fun updateNotifications(isEnabled: Boolean): Result<Unit>
    suspend fun logout(): Result<Unit>
} 