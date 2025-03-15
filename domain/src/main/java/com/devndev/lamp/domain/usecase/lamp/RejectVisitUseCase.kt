package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.RejectVisitParam
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class RejectVisitUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(lampId: Int, rejectVisitParam: RejectVisitParam) {
        lampRepository.rejectVisit(lampId, rejectVisitParam)
    }
}
