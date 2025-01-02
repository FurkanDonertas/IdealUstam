package com.furkandonertas.idealustam.features.settings.domain.model

data class UserSettings(
    val username: String,
    val email: String,
    val profileImageUrl: String? = null,
    val isDarkTheme: Boolean = false,
    val isNotificationsEnabled: Boolean = true
) 