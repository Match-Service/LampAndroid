package com.devndev.lamp.domain.usecase.login

import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(token: String) {
        loginRepository.setToken(token)
    }
}
