package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.repository.SignUpRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ImageUploadUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(file: MultipartBody.Part): Response<Void> {
        return signUpRepository.uploadImage(file)
    }
}
