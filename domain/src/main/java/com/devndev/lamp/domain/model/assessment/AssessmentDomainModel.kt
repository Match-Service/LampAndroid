package com.devndev.lamp.domain.model.assessment

data class AssessmentDomainModel(
    val lampId: Int,
    val lampName: String,
    val meetingTime: String,
    val users: List<UserDomainModel>
)

data class UserDomainModel(
    val userId: Int,
    val name: String,
    val birth: String,
    val profileImageUrl: String
)
