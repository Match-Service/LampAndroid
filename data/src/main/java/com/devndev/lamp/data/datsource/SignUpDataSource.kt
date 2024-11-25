package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.ValidateNameRequest
import retrofit2.Response

interface SignUpDataSource {
    suspend fun validateName(validateNameRequest: ValidateNameRequest): Response<Void>
}
