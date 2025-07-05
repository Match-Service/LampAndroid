package com.devndev.lamp.data.dto.response.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssessmentListResponse(
    @Json(name = "lampMatchId")
    val lampMatchId: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "meetingTime")
    val meetingTime: String
)

fun AssessmentListResponse.toDomainModel(): AssessmentListDomainModel {
    return AssessmentListDomainModel(
        lampMatchId = lampMatchId,
        title = title,
        meetingTime = meetingTime
    )
}
