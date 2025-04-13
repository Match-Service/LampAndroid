package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.EditImageParam
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class EditImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(files: EditImageParam): Boolean {
        return userRepository.editImage(files)
    }
}
