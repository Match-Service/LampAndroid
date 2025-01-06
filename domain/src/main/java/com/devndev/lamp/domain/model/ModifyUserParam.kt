package com.devndev.lamp.domain.model

data class ModifyUserParam(
    val name: String,
    val job: String,
    val jobName: String,
    val gender: String,
    val birth: String,
    val instagramId: String,
    val bio: String,
    val profileImages: List<String>,
    val alarmSetting: List<AlarmSetting>,
    val bioQuestion: List<BioQuestion>,
    val pushToken: String
)
