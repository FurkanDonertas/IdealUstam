package com.furkandonertas.idealustam.features.auth.data.model

data class LoginResponse(
    val token: String,
    val expiresIn: Long
) 