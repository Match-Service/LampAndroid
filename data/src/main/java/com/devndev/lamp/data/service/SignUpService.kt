package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.signup.SignUpRequest
import com.devndev.lamp.data.dto.request.signup.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.signup.ValidateNameRequest
import com.devndev.lamp.data.dto.response.signup.ProfileImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignUpService {
    @POST("api/v1/auth/validation/name")
    suspend fun validateName(
        @Body validateNameRequest: ValidateNameRequest
    ): Response<Void>

    @POST("api/v1/auth/validation/instagram")
    suspend fun validateInstagram(
        @Body validateInstagramRequest: ValidateInstagramRequest
    ): Response<Void>

    @Multipart
    @POST("api/v1/auth/profile-images")
    suspend fun uploadImages(
        @Part files: List<MultipartBody.Part>
    ): List<ProfileImageResponse>

    @POST("api/v1/auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<Void>
}
