package com.devndev.lamp.data.dto.request

import com.devndev.lamp.domain.model.AlarmSetting
import com.devndev.lamp.domain.model.BioQuestion
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModifyUserRequest(
    @Json(name = "name")
    val name: String,
    @Json(name = "job")
    val job: String,
    @Json(name = "jobName")
    val jobName: String,
    @Json(name = "pushToken")
    val gender: String,
    @Json(name = "birth")
    val birth: String,
    @Json(name = "instagramId")
    val instagramId: String,
    @Json(name = "bio")
    val bio: String,
    @Json(name = "profileImages")
    val profileImages: List<String>,
    @Json(name = "alarmSetting")
    val alarmSetting: List<AlarmSetting>,
    @Json(name = "bioQuestion")
    val bioQuestion: List<BioQuestion>,
    @Json(name = "pushToken")
    val pushToken: String
)
