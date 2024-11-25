package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.ValidateNameRequest
import com.devndev.lamp.data.service.SignUpService
import retrofit2.Response
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(
    private val signUpService: SignUpService
) : SignUpDataSource {
    override suspend fun validateName(validateNameRequest: ValidateNameRequest): Response<Void> {
        return signUpService.validateName(validateNameRequest)
    }
}
