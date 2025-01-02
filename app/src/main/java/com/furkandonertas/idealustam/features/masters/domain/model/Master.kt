package com.furkandonertas.idealustam.features.masters.domain.model

data class Master(
    val id: Int,
    val name: String,
    val specialization: String,
    val imageUrl: String? = null,
    val rating: Float,
    val reviewCount: Int,
    val experience: Int, // Yıl cinsinden
    val location: String,
    val isAvailable: Boolean,
    val services: List<String>
) 