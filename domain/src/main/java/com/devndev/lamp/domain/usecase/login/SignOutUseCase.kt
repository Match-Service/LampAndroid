package com.devndev.lamp.domain.usecase.login

import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return runCatching { loginRepository.removeToken() }
    }
}
