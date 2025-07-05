package com.devndev.lamp.domain.usecase.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentParam
import com.devndev.lamp.domain.repository.AssessmentRepository
import javax.inject.Inject

class AssessmentUseCase @Inject constructor(
    private val assessmentRepository: AssessmentRepository
) {
    suspend operator fun invoke(assessmentParam: AssessmentParam) {
        assessmentRepository.assessment(assessmentParam)
    }
}
