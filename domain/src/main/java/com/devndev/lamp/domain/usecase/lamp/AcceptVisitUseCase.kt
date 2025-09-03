package com.devndev.lamp.domain.usecase.lamp

import com.devndev.lamp.domain.model.lamp.AcceptVisitParam
import com.devndev.lamp.domain.repository.LampRepository
import retrofit2.Response
import javax.inject.Inject

class AcceptVisitUseCase @Inject constructor(
    private val lampRepository: LampRepository
) {
    suspend operator fun invoke(acceptVisitParam: AcceptVisitParam): Result<Response<Unit>> {
        return runCatching {
            lampRepository.acceptVisit(acceptVisitParam)
        }
    }
}
