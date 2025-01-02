package com.furkandonertas.idealustam.features.onboarding.masters.domain.usecase

import com.furkandonertas.idealustam.features.onboarding.masters.domain.model.Master
import com.furkandonertas.idealustam.features.onboarding.masters.domain.repository.MasterRepository

class GetMastersUseCase(private val repository: MasterRepository) {
    suspend operator fun invoke(): Result<List<Master>> {
        return repository.getMasters()
    }
} 