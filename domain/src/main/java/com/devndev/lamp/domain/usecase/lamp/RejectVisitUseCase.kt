package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.RejectVisitParam
import com.devndev.lamp.domain.repository.LampRepository
import retrofit2.Response
import javax.inject.Inject

class RejectVisitUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(rejectVisitParam: RejectVisitParam): Result<Response<Unit>> {
        return runCatching {
            lampRepository.rejectVisit(rejectVisitParam)
        }
    }
}
