package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.ValidateNameRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("api/v1/auth/validation/name")
    suspend fun validateName(
        @Body validateNameRequest: ValidateNameRequest
    ): Response<Void>
}
