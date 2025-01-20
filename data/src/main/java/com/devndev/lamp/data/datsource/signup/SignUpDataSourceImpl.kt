package com.devndev.lamp.data.datsource.signup

import com.devndev.lamp.data.dto.request.signup.SignUpRequest
import com.devndev.lamp.data.dto.request.signup.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.signup.ValidateNameRequest
import com.devndev.lamp.data.dto.response.signup.ProfileImageResponse
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
