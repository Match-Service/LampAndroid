package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.CreateLampParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class CreateLampUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(createLampParam: CreateLampParam): Int {
        return lampRepository.createLamp(createLampParam)
    }
}
