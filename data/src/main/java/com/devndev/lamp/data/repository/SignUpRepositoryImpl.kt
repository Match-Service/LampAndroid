package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.SignUpDataSource
import com.devndev.lamp.data.dto.request.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.ValidateNameRequest
import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.model.ValidateNameParam
import com.devndev.lamp.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpDataSource: SignUpDataSource) :
    SignUpRepository {
    override suspend fun validateName(validateNameParam: ValidateNameParam): Boolean {
        val validateNameRequest = ValidateNameRequest(name = validateNameParam.name)
        val code = signUpDataSource.validateName(validateNameRequest).code()
        return code == 200
    }

    override suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean {
        val validateInstagramRequest =
            ValidateInstagramRequest(instagramId = validateInstagramParam.instagramId)
        val code = signUpDataSource.validateInstagram(validateInstagramRequest).code()
//        return code == 200
        // 서버 미완성으로 인해 true 반환
        return true
    }
}
