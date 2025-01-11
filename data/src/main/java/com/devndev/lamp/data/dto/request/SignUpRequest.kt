package com.devndev.lamp.data.dto.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "signupAuthRequest") val signupAuthRequest: SignUpAuthRequest,
    @Json(name = "user") val user: User
)

@JsonClass(generateAdapter = true)
data class SignUpAuthRequest(
    @Json(name = "signUpToken") val signUpToken: String
)

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "name") val name: String,
    @Json(name = "job") val job: String,
    @Json(name = "jobName") val jobName: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "birth") val birth: String,
    @Json(name = "instagramId") val instagramId: String,
    @Json(name = "bio") val bio: String,
    @Json(name = "profileImages") val profileImages: List<String>,
    @Json(name = "alarmSetting") val alarmSetting: AlarmSetting,
    @Json(name = "bioQuestions") val bioQuestions: List<BioQuestion>,
    @Json(name = "pushToken") val pushToken: String
)

@JsonClass(generateAdapter = true)
data class AlarmSetting(
    @Json(name = "allPush") val allPush: Boolean,
    @Json(name = "lampInvite") val lampInvite: Boolean,
    @Json(name = "lampVisit") val lampVisit: Boolean,
    @Json(name = "newMatch") val newMatch: Boolean,
    @Json(name = "receiveBadge") val receiveBadge: Boolean,
    @Json(name = "receiveMessage") val receiveMessage: Boolean
)

@JsonClass(generateAdapter = true)
data class BioQuestion(
    @Json(name = "question") val question: String,
    @Json(name = "answer") val answer: String
)
