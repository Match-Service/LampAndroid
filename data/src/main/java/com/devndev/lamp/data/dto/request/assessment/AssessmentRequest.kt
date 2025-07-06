package com.devndev.lamp.data.dto.request.assessment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AssessmentRequest(
    @Json(name = "score")
    val score: Int,
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "lampMatchId")
    val lampMatchId: Int,
    @Json(name = "lampAssessmentUserInfos")
    val lampAssessmentUserInfos: List<AssessmentUserInfo>
)

@JsonClass(generateAdapter = true)
data class AssessmentUserInfo(
    @Json(name = "question")
    val question: String,
    @Json(name = "score")
    val score: Int,
    @Json(name = "userId")
    val userId: Int
)
