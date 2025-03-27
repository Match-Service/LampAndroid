package com.devndev.lamp.domain.usecase.socket

import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class DisconnectSocketUseCase @Inject constructor(
    private val lampSocketRepository: LampSocketRepository
) {
    suspend operator fun invoke() {
        lampSocketRepository.disconnect()
    }
}
