package com.devndev.lamp.domain.usecase.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.repository.AssessmentRepository
import javax.inject.Inject

class GetAssessmentListUseCase @Inject constructor(
    private val assessmentRepository: AssessmentRepository
) {
    suspend operator fun invoke(): Result<List<AssessmentListDomainModel>> {
        return runCatching {
            assessmentRepository.getAssessmentList()
        }
    }
}
