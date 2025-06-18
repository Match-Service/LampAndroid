package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class EditLampUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(createLampParam: CreateLampParam): Result<Unit> {
        return kotlin.runCatching {
            lampRepository.editLamp(createLampParam)
        }
    }
}
