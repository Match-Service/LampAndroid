package com.devndev.lamp.domain.model.user

import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Result<Unit> {
        return runCatching {
            userRepository.deleteAccount(userId)
        }
    }
}
