package com.devndev.lamp.data.dto.response.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssessmentListResponse(
    @Json(name = "lampMatchId")
    val lampMatchId: Int,
    @Json(name = "myLampName")
    val myLampName: String,
    @Json(name = "otherLampName")
    val otherLampName: String,
    @Json(name = "meetingTime")
    val meetingTime: String,
    @Json(name = "meetingUserCount")
    val meetingUserCount: Int
)

fun AssessmentListResponse.toDomainModel(): AssessmentListDomainModel {
    return AssessmentListDomainModel(
        lampMatchId = lampMatchId,
        myLampName = myLampName,
        otherLampName = otherLampName,
        meetingTime = meetingTime,
        meetingUserCount = meetingUserCount
    )
}
