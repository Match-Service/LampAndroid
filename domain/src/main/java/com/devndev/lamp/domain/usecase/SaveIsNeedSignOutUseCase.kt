package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.repository.GoogleTokenRepository
import javax.inject.Inject

class SaveIsNeedSignOutUseCase @Inject constructor(
    private val googleTokenRepository: GoogleTokenRepository
) {
    operator fun invoke(isNeedSignOut: Boolean) {
        googleTokenRepository.saveIsNeedSignOut(isNeedSignOut)
    }
}
