package com.devndev.lamp.domain.model.assessment

data class AssessmentListDomainModel(
    val lampMatchId: Int,
    val myLampName: String,
    val otherLampName: String,
    val meetingTime: String,
    val meetingUserCount: Int
)
