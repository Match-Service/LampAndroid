package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class GetMyInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<MyInfoDomainModel> {
        return kotlin.runCatching {
            userRepository.getMyInfo()
        }
    }
}
