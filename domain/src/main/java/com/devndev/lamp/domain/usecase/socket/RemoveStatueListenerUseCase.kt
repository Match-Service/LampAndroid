package com.devndev.lamp.domain.usecase.socket

import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class RemoveStatueListenerUseCase @Inject constructor(
    private val lampSocketRepository: LampSocketRepository
) {
    operator fun invoke() {
        lampSocketRepository.removeStatusListener()
    }
}
