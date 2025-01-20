package com.devndev.lamp.domain.usecase.signup

import com.devndev.lamp.domain.model.signup.ProfileImageDomainModel
import com.devndev.lamp.domain.repository.SignUpRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageUploadUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(files: List<MultipartBody.Part>): List<ProfileImageDomainModel> {
        return signUpRepository.uploadImages(files)
    }
}
