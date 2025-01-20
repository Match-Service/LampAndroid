package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String): List<UserDomainModel> {
        return userRepository.searchUser(name)
    }
}
