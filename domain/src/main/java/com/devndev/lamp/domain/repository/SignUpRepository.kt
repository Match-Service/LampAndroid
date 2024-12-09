package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.ProfileImageDomainModel
import com.devndev.lamp.domain.model.SignUpParam
import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.model.ValidateNameParam
import okhttp3.MultipartBody
import retrofit2.Response

interface SignUpRepository {
    suspend fun validateName(validateNameParam: ValidateNameParam): Boolean
    suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean
    suspend fun uploadImages(files: List<MultipartBody.Part>): List<ProfileImageDomainModel>
    suspend fun signUp(signUpParam: SignUpParam): Response<Void>
}
