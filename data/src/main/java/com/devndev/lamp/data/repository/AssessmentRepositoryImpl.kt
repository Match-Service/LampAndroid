package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.assessment.AssessmentDataSource
import com.devndev.lamp.data.dto.request.assessment.AssessmentRequest
import com.devndev.lamp.data.dto.request.assessment.AssessmentUserInfo
import com.devndev.lamp.data.dto.response.assessment.toDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentParam
import com.devndev.lamp.domain.repository.AssessmentRepository
import javax.inject.Inject

class AssessmentRepositoryImpl @Inject constructor(
    private val assessmentDataSource: AssessmentDataSource
) : AssessmentRepository {
    override suspend fun getAssessment(lampMatchId: Int): AssessmentDomainModel {
        return assessmentDataSource.getAssessment(lampMatchId).toDomainModel()
    }

    override suspend fun getAssessmentList(): List<AssessmentListDomainModel> {
        return assessmentDataSource.getAssessmentList().map { it.toDomainModel() }
    }

    override suspend fun assessment(assessmentParam: AssessmentParam) {
        val assessmentUserInfos = assessmentParam.lampAssessmentUserInfos.map { info ->
            AssessmentUserInfo(
                question = info.question,
                score = info.score,
                userId = info.userId
            )
        }

        assessmentDataSource.assessment(
            AssessmentRequest(
                score = assessmentParam.score,
                lampId = assessmentParam.lampId,
                lampMatchId = assessmentParam.lampMatchId,
                lampAssessmentUserInfos = assessmentUserInfos
            )
        )
    }
}
