package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.SignUpRequest
import com.devndev.lamp.data.dto.request.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.ValidateNameRequest
import okhttp3.MultipartBody
import retrofit2.Response

interface SignUpDataSource {
    suspend fun validateName(validateNameRequest: ValidateNameRequest): Response<Void>
    suspend fun validateInstagram(validateInstagramRequest: ValidateInstagramRequest): Response<Void>
    suspend fun uploadImage(file: MultipartBody.Part): Response<Void>
    suspend fun signUp(signUpRequest: SignUpRequest): Response<Void>
}
