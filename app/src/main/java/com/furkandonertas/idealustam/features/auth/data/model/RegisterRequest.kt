package com.furkandonertas.idealustam.features.auth.data.model

data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
) 