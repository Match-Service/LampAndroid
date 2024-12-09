package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.SignUpRequest
import com.devndev.lamp.data.dto.request.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.ValidateNameRequest
import com.devndev.lamp.data.dto.response.ProfileImageResponse
import com.devndev.lamp.data.service.SignUpService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(
    private val signUpService: SignUpService
) : SignUpDataSource {
    override suspend fun validateName(validateNameRequest: ValidateNameRequest): Response<Void> {
        return signUpService.validateName(validateNameRequest)
    }

    override suspend fun validateInstagram(validateInstagramRequest: ValidateInstagramRequest): Response<Void> {
        return signUpService.validateInstagram(validateInstagramRequest)
    }

    override suspend fun uploadImages(files: List<MultipartBody.Part>): List<ProfileImageResponse> {
        return signUpService.uploadImages(files)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Response<Void> {
        return signUpService.signUp(signUpRequest)
    }
}
