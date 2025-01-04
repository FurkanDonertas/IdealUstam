package com.furkandonertas.idealustam.features.auth.data.repository

import User
import VerifyRequest

import com.furkandonertas.idealustam.core.network.AuthApi
import com.furkandonertas.idealustam.features.auth.data.local.TokenManager
import com.furkandonertas.idealustam.features.auth.data.model.LoginRequest
import com.furkandonertas.idealustam.features.auth.data.model.LoginResponse
import com.furkandonertas.idealustam.features.auth.data.model.RegisterRequest
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    tokenManager.saveToken(loginResponse.token)
                    Result.success(loginResponse)
                } ?: Result.failure(Exception("Boş yanıt"))
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception(errorBody ?: "Bilinmeyen hata"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String, username: String): Result<User> {
        return try {
            val response = api.signup(RegisterRequest(email, password, username))
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception("Boş yanıt"))
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception(errorBody ?: "Kayıt başarısız"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyEmail(email: String, code: String): Result<Unit> {
        return try {
            val response = api.verifyEmail(VerifyRequest(email, code))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Doğrulama başarısız"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 