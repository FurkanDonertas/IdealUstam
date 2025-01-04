package com.furkandonertas.idealustam.core.network

import User
import VerifyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import com.furkandonertas.idealustam.features.auth.data.model.LoginRequest
import com.furkandonertas.idealustam.features.auth.data.model.RegisterRequest

import com.furkandonertas.idealustam.features.auth.data.model.LoginResponse

interface AuthApi {
    @POST("auth/signup")
    suspend fun signup(@Body request: RegisterRequest): Response<User>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/verify")
    suspend fun verifyEmail(@Body request: VerifyRequest): Response<Unit>

    @POST("auth/resend")
    suspend fun resendVerificationCode(@Query("email") email: String): Response<Unit>
} 