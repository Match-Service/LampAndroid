package com.devndev.lamp.domain.model.assessment

data class AssessmentParam(
    val score: Int,
    val lampId: Int,
    val lampMatchId: Int,
    val lampAssessmentUserInfos: List<AssessmentUserInfo>
)

data class AssessmentUserInfo(
    val question: String,
    val score: Int,
    val userId: Int
)
