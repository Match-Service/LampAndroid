package com.devndev.lamp.domain.usecase.socket

import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class AddStatusListenerUseCase @Inject constructor(
    private val lampSocketRepository: LampSocketRepository
) {
    operator fun invoke(
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit
    ) {
        lampSocketRepository.addStatusListener(onMessage, onUpdatedMessage)
    }
}
