package com.devndev.lamp.domain.usecase.login

import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class SaveIsNeedSignOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(isNeedSignOut: Boolean) {
        loginRepository.saveIsNeedSignOut(isNeedSignOut)
    }
}
