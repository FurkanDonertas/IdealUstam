package com.furkandonertas.idealustam.features.onboarding.masters.domain.model

data class Master(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Float,
    val imageUrl: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val isFavorite: Boolean
) 