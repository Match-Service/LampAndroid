package com.devndev.lamp.domain.usecase.config

import com.devndev.lamp.domain.repository.ConfigRepository
import javax.inject.Inject

class SaveIsFirstOpenUseCase @Inject constructor(
    private val configRepository: ConfigRepository
) {
    suspend operator fun invoke(isFirstOpen: Boolean): Result<Unit> {
        return runCatching {
            configRepository.saveIsFirstOpen(isFirstOpen)
        }
    }
}
