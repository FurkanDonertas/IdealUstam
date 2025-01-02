package com.furkandonertas.idealustam.features.onboarding.masters.data.model.response

import com.furkandonertas.idealustam.features.onboarding.masters.domain.model.Master

data class MasterResponse(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Float,
    val imageUrl: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val isFavorite: Boolean
) {
    fun toDomain() = Master(
        id = id,
        name = name,
        specialty = specialty,
        rating = rating,
        imageUrl = imageUrl,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime,
        isFavorite = isFavorite
    )
} 