package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class AcceptInviteUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(lampId: Int, acceptInviteParam: AcceptInviteParam) {
        lampRepository.acceptInvite(lampId = lampId, acceptInviteParam = acceptInviteParam)
    }
}
