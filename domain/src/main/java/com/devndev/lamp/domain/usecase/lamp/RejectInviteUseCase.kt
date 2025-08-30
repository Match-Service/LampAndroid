package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class RejectInviteUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(rejectInviteParam: RejectInviteParam): Result<Int> {
        return runCatching {
            lampRepository.rejectInvite(rejectInviteParam = rejectInviteParam)
        }
    }
}
