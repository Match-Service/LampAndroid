package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class GetRecentUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<UserDomainModel>> {
        return runCatching {
            userRepository.getRecentUsers()
        }
    }
}
