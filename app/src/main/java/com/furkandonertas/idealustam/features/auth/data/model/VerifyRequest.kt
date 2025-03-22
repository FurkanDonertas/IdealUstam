package com.furkandonertas.idealustam.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class VerifyRequest(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("verificationCode")
    val verificationCode: String
) 