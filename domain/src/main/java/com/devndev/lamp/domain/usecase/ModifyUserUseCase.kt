package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.ModifyUserParam
import com.devndev.lamp.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class ModifyUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(modifyUserParam: ModifyUserParam): Response<Void> {
        return userRepository.modifyUser(modifyUserParam)
    }
}
