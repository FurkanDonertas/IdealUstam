package com.furkandonertas.idealustam.core.network

import com.furkandonertas.idealustam.features.auth.data.model.*
import com.furkandonertas.idealustam.features.auth.domain.model.User
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    suspend fun signup(@Body request: RegisterRequest): Response<User>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/verify")
    suspend fun verifyEmail(@Body request: VerifyRequest): Response<String>

    @Headers("Content-Type: application/json")
    @POST("auth/resend")
    suspend fun resendVerificationCode(@Query("email") email: String): Response<String>
} 