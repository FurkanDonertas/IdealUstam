package com.furkandonertas.idealustam.features.home.domain.model

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val year: Int,
    val plateNumber: String,
    val imageUrl: String? = null
) 