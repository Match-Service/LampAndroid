package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class ExitLampUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(lampId: Int) {
        lampRepository.exitLamp(lampId)
    }
}
