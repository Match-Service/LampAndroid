package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class ModifyUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(modifyUserParam: ModifyUserParam): Result<Boolean> {
        return runCatching {
            userRepository.modifyUser(modifyUserParam)
        }
    }
}
