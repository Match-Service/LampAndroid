package com.devndev.lamp.domain.usecase.config

import com.devndev.lamp.domain.repository.ConfigRepository
import javax.inject.Inject

class GetIsFirstOpenUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return runCatching {
            configRepository.getIsFirstOpen()
        }
    }
}
