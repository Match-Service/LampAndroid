package com.devndev.lamp.domain.usecase.login

import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Boolean {
        return loginRepository.isUserLoggedIn()
    }
}
