package com.furkandonertas.idealustam.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    
    @SerializedName("expiresIn")
    val expiresIn: Long
) 