package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.model.ValidateNameParam
import okhttp3.MultipartBody
import retrofit2.Response

interface SignUpRepository {
    suspend fun validateName(validateNameParam: ValidateNameParam): Boolean
    suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean
    suspend fun uploadImage(file: MultipartBody.Part): Response<Void>
}
