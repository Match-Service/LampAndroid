package com.devndev.lamp.domain.usecase.lampmatch

import com.devndev.lamp.domain.repository.LampMatchRepository
import javax.inject.Inject

class StartMatchUseCase @Inject constructor(
    private val lampMatchRepository: LampMatchRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatching {
            lampMatchRepository.startMatch()
        }
    }
}
