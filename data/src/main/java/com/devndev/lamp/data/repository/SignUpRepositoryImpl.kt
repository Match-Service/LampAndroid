package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.SignUpDataSource
import com.devndev.lamp.data.dto.request.ValidateNameRequest
import com.devndev.lamp.domain.model.ValidateNameParam
import com.devndev.lamp.domain.repository.SignUpRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpDataSource: SignUpDataSource) :
    SignUpRepository {
    override suspend fun validateName(validateNameParam: ValidateNameParam): Response<Void> {
        val validateNameRequest = ValidateNameRequest(name = validateNameParam.name)
        return signUpDataSource.validateName(validateNameRequest)
    }
}
