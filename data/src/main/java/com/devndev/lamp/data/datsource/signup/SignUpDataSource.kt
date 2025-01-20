package com.devndev.lamp.data.datsource.signup

import com.devndev.lamp.data.dto.request.signup.SignUpRequest
import com.devndev.lamp.data.dto.request.signup.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.signup.ValidateNameRequest
import com.devndev.lamp.data.dto.response.signup.ProfileImageResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface SignUpDataSource {
    suspend fun validateName(validateNameRequest: ValidateNameRequest): Response<Void>
    suspend fun validateInstagram(validateInstagramRequest: ValidateInstagramRequest): Response<Void>
    suspend fun uploadImages(files: List<MultipartBody.Part>): List<ProfileImageResponse>
    suspend fun signUp(signUpRequest: SignUpRequest): Response<Void>
}
