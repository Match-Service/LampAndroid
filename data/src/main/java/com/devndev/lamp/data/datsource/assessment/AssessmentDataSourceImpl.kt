package com.devndev.lamp.data.datsource.assessment

import com.devndev.lamp.data.dto.request.assessment.AssessmentRequest
import com.devndev.lamp.data.dto.response.assessment.AssessmentListResponse
import com.devndev.lamp.data.dto.response.assessment.AssessmentResponse
import com.devndev.lamp.data.service.AssessmentService
import javax.inject.Inject

class AssessmentDataSourceImpl @Inject constructor(
    private val assessmentService: AssessmentService
) : AssessmentDataSource {
    override suspend fun getAssessment(lampMatchId: Int): AssessmentResponse {
        return assessmentService.getAssessment(lampMatchId)
    }

    override suspend fun getAssessmentList(): List<AssessmentListResponse> {
        return assessmentService.getAssessmentList()
    }

    override suspend fun assessment(assessmentRequest: AssessmentRequest) {
        assessmentService.assessment(assessmentRequest)
    }
}
