package com.furkandonertas.idealustam.features.services.domain.model

data class Service(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String? = null,
    val price: Double,
    val duration: Int, // Dakika cinsinden
    val rating: Float,
    val reviewCount: Int
) 