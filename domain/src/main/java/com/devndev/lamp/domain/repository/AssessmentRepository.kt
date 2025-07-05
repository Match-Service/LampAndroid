package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentParam

interface AssessmentRepository {
    suspend fun getAssessment(lampMatchId: Int): AssessmentDomainModel
    suspend fun getAssessmentList(): List<AssessmentListDomainModel>
    suspend fun assessment(assessmentParam: AssessmentParam)
}
