package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.ValidateNameParam
import retrofit2.Response

interface SignUpRepository {
    suspend fun validateName(validateNameParam: ValidateNameParam): Response<Void>
}
