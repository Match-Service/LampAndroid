package com.devndev.lamp.domain.usecase.user

import com.devndev.lamp.domain.model.user.PushTokenParam
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class PutPushTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(pushTokenParam: PushTokenParam) {
        userRepository.putPushToken(pushTokenParam)
    }
}
