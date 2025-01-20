package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.signup.ProfileImageDomainModel
import com.devndev.lamp.domain.model.signup.SignUpParam
import com.devndev.lamp.domain.model.signup.ValidateInstagramParam
import com.devndev.lamp.domain.model.signup.ValidateNameParam
import okhttp3.MultipartBody
import retrofit2.Response

interface SignUpRepository {
    suspend fun validateName(validateNameParam: ValidateNameParam): Boolean
    suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean
    suspend fun uploadImages(files: List<MultipartBody.Part>): List<ProfileImageDomainModel>
    suspend fun signUp(signUpParam: SignUpParam): Response<Void>
}
