package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.GoogleTokenParam
import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class GoogleAuthUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel {
        return loginRepository.getGoogleAuth(googleTokenParam)
    }
}
