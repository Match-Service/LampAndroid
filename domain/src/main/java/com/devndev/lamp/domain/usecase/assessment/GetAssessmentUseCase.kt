package com.devndev.lamp.domain.usecase.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.domain.repository.AssessmentRepository
import javax.inject.Inject

class GetAssessmentUseCase @Inject constructor(
    private val assessmentRepository: AssessmentRepository
) {
    suspend operator fun invoke(lampMatchId: Int): Result<AssessmentDomainModel> {
        return runCatching {
            assessmentRepository.getAssessment(lampMatchId)
        }
    }
}
