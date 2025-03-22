package com.furkandonertas.idealustam.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("status")
    val status: String,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("error")
    val error: String? = null
) 