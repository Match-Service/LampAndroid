package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class CheckIsNeedSignOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(): Boolean {
        return loginRepository.getIsNeedSignOut()
    }
}
