package com.furkandonertas.idealustam.features.auth.data.repository

import android.util.Log
import com.furkandonertas.idealustam.core.network.AuthApi
import com.furkandonertas.idealustam.features.auth.data.local.TokenManager
import com.furkandonertas.idealustam.features.auth.data.model.LoginRequest
import com.furkandonertas.idealustam.features.auth.data.model.LoginResponse
import com.furkandonertas.idealustam.features.auth.data.model.RegisterRequest
import com.furkandonertas.idealustam.features.auth.data.model.VerifyRequest
import com.furkandonertas.idealustam.features.auth.domain.model.User
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
    private val gson: Gson
) {
    suspend fun register(email: String, password: String, username: String): Result<User> {
        return try {
            val request = RegisterRequest(email, password, username)
            Log.d("AuthRepository", "Register request: $request")
            Log.d("AuthRepository", "Request JSON: ${gson.toJson(request)}")
            
            val response = api.signup(request)
            Log.d("AuthRepository", "Response code: ${response.code()}")
            Log.d("AuthRepository", "Response headers: ${response.headers()}")
            
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    Log.d("AuthRepository", "Register success: $user")
                    Result.success(user)
                } else {
                    Log.e("AuthRepository", "Register success but user is null")
                    Result.failure(Exception("Kullanıcı bilgileri alınamadı"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Register Error: $errorBody")
                Log.e("AuthRepository", "Error response code: ${response.code()}")
                Result.failure(Exception(errorBody ?: "Kayıt başarısız"))
            }
        } catch (e: IOException) {
            Log.e("AuthRepository", "Network Exception: ${e.message}")
            Log.e("AuthRepository", "Stack trace: ", e)
            Result.failure(Exception("İnternet bağlantınızı kontrol edin"))
        } catch (e: Exception) {
            Log.e("AuthRepository", "Register Exception: ${e.message}")
            Log.e("AuthRepository", "Stack trace: ", e)
            Result.failure(Exception("Bir hata oluştu: ${e.message}"))
        }
    }

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            Log.d("AuthRepository", "Login attempt for email: $email")
            val request = LoginRequest(email, password)
            Log.d("AuthRepository", "Login request: ${gson.toJson(request)}")
            
            val response = api.login(request)
            Log.d("AuthRepository", "Login response code: ${response.code()}")
            Log.d("AuthRepository", "Login response headers: ${response.headers()}")
            
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    Log.d("AuthRepository", "Login successful: $loginResponse")
                    tokenManager.saveToken(loginResponse.token, loginResponse.expiresIn)
                    Result.success(loginResponse)
                } ?: run {
                    Log.e("AuthRepository", "Login response body is null")
                    Result.failure(Exception("Boş yanıt"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Login Error: $errorBody")
                Log.e("AuthRepository", "Error response code: ${response.code()}")
                Result.failure(Exception(errorBody ?: "Giriş başarısız"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login Exception: ${e.message}")
            Log.e("AuthRepository", "Stack trace: ", e)
            Result.failure(e)
        }
    }

    suspend fun verifyEmail(email: String, code: String): Result<String> {
        return try {
            val response = api.verifyEmail(VerifyRequest(email, code))
            if (response.isSuccessful) {
                val message = response.body()
                Result.success(message ?: "Email doğrulandı")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Verify Error: $errorBody")
                Result.failure(Exception(errorBody ?: "Doğrulama başarısız"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Verify Exception: ${e.message}")
            Result.failure(e)
        }
    }

    suspend fun resendVerificationCode(email: String): Result<String> {
        return try {
            val response = api.resendVerificationCode(email)
            if (response.isSuccessful) {
                val message = response.body()
                Result.success(message ?: "Doğrulama kodu gönderildi")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("AuthRepository", "Resend Code Error: $errorBody")
                Result.failure(Exception(errorBody ?: "Kod gönderilemedi"))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Resend Code Exception: ${e.message}")
            Result.failure(e)
        }
    }
}