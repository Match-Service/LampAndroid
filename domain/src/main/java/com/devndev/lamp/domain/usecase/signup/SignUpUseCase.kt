package com.devndev.lamp.domain.usecase.signup

import com.devndev.lamp.domain.model.signup.SignUpParam
import com.devndev.lamp.domain.repository.SignUpRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(signUpParam: SignUpParam): Response<Void> {
        return signUpRepository.signUp(signUpParam)
    }
}
