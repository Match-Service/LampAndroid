package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.LampDomainModel
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class GetMyLampUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(): LampDomainModel {
        return lampRepository.getMyLamp()
    }
}
