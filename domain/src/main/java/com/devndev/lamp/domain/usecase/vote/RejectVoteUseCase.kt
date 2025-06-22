package com.devndev.lamp.domain.usecase.vote

import com.devndev.lamp.domain.model.lamp.RejectVoteParam
import com.devndev.lamp.domain.repository.LampMatchRepository
import javax.inject.Inject

class RejectVoteUseCase @Inject constructor(
    private val lampMatchRepository: LampMatchRepository
) {
    suspend operator fun invoke(rejectVoteParam: RejectVoteParam) {
        lampMatchRepository.reject(rejectVoteParam)
    }
}
