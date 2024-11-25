package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.repository.GoogleTokenRepository
import javax.inject.Inject

class CheckIsNeedSignOutUseCase @Inject constructor(
    private val googleTokenRepository: GoogleTokenRepository
) {
    operator fun invoke(): Boolean {
        return googleTokenRepository.getIsNeedSignOut()
    }
}
