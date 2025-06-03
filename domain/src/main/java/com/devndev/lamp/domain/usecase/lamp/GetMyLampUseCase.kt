package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class GetMyLampUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(): Result<LampDomainModel> {
        return runCatching {
            lampRepository.getMyLamp()
        }
    }
}
