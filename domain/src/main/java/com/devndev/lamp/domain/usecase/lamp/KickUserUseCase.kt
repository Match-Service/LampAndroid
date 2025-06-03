package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class KickUserUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(kickUserParam: KickUserParam): Result<Unit> {
        return runCatching {
            lampRepository.kickUser(kickUserParam)
        }
    }
}
