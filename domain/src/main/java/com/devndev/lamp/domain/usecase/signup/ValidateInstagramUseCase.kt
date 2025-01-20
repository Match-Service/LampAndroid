package com.devndev.lamp.domain.usecase.signup

import com.devndev.lamp.domain.model.signup.ValidateInstagramParam
import com.devndev.lamp.domain.repository.SignUpRepository
import javax.inject.Inject

class ValidateInstagramUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(validateInstagramParam: ValidateInstagramParam): Boolean {
        return signUpRepository.validateInstagram(validateInstagramParam)
    }
}
