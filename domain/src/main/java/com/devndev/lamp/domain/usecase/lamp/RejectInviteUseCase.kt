package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.repository.LampRepository
import retrofit2.Response
import javax.inject.Inject

class RejectInviteUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(rejectInviteParam: RejectInviteParam): Result<Response<Unit>> {
        return runCatching {
            lampRepository.rejectInvite(rejectInviteParam = rejectInviteParam)
        }
    }
}
