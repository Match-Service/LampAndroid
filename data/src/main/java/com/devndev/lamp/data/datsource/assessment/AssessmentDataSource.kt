package com.devndev.lamp.data.datsource.assessment

import com.devndev.lamp.data.dto.request.assessment.AssessmentRequest
import com.devndev.lamp.data.dto.response.assessment.AssessmentListResponse
import com.devndev.lamp.data.dto.response.assessment.AssessmentResponse

interface AssessmentDataSource {
    suspend fun getAssessment(lampMatchId: Int): AssessmentResponse
    suspend fun getAssessmentList(): List<AssessmentListResponse>
    suspend fun assessment(assessmentRequest: AssessmentRequest)
}
