package com.devndev.lamp.data.dto.response.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.domain.model.assessment.UserDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssessmentResponse(
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "lampName")
    val lampName: String,
    @Json(name = "meetingTime")
    val meetingTime: String,
    @Json(name = "users")
    val users: List<User>
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "birth")
    val birth: String,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String
)

fun AssessmentResponse.toDomainModel(): AssessmentDomainModel {
    return AssessmentDomainModel(
        lampId = lampId,
        lampName = lampName,
        meetingTime = meetingTime,
        users = users.map { it.toDomainModel() }
    )
}

fun User.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        userId = userId,
        name = name,
        birth = birth,
        profileImageUrl = profileImageUrl
    )
}
