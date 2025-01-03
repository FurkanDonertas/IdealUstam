package com.furkandonertas.idealustam.features.masters.domain.model

data class Master(
    val id: String,
    val name: String,
    val specialty: String,
    val rating: Float,
    val experience: Int,
    val location: String,
    val imageUrl: String?,
    val isFavorite: Boolean
) 