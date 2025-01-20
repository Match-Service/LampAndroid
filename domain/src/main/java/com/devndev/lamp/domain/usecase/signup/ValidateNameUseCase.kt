package com.devndev.lamp.domain.usecase.signup

import com.devndev.lamp.domain.model.signup.ValidateNameParam
import com.devndev.lamp.domain.repository.SignUpRepository
import javax.inject.Inject

class ValidateNameUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(validateNameParam: ValidateNameParam): Boolean {
        return signUpRepository.validateName(validateNameParam)
    }
}
