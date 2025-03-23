package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.UserStatusDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserStatusUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserStatusDomainModel {
        return userRepository.getUserStatus()
    }
}
