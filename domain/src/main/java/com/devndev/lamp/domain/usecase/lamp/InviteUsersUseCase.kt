package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class InviteUsersUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(inviteUsersParam: InviteUsersParam) {
        lampRepository.inviteUser(inviteUsersParam)
    }
}
