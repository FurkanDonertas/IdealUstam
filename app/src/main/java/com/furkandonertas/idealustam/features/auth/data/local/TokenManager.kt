package com.furkandonertas.idealustam.features.auth.data.local

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_TOKEN_EXPIRY = "auth_token_expiry"
    }

    fun saveToken(token: String, expiresIn: Long = 3600) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        sharedPreferences.edit()
            .putString(KEY_TOKEN, token)
            .putLong(KEY_TOKEN_EXPIRY, expiryTime)
            .apply()
    }

    fun getToken(): String? {
        val expiryTime = sharedPreferences.getLong(KEY_TOKEN_EXPIRY, 0)
        return if (System.currentTimeMillis() < expiryTime) {
            sharedPreferences.getString(KEY_TOKEN, null)
        } else {
            clearToken()
            null
        }
    }

    fun clearToken() {
        sharedPreferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_TOKEN_EXPIRY)
            .apply()
    }

    fun isTokenValid(): Boolean {
        val expiryTime = sharedPreferences.getLong(KEY_TOKEN_EXPIRY, 0)
        return System.currentTimeMillis() < expiryTime && getToken() != null
    }
} 