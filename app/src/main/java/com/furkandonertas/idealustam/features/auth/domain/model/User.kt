package com.furkandonertas.idealustam.features.auth.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("username")
    val username: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("enabled")
    val enabled: Boolean
) 