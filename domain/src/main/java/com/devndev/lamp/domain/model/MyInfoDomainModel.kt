package com.devndev.lamp.domain.model

data class MyInfoDomainModel(
    val userId: Int,
    val name: String,
    val job: String,
    val jobName: String?,
    val gender: String,
    val birth: String,
    val instagramId: String?,
    val bio: String?,
    val profileImages: List<ProfileImageForMyInfo>,
    val alarmSetting: AlarmSettingForMyInfo,
    val bioQuestions: List<BioQuestion>
)

data class ProfileImageForMyInfo(
    val profileImageId: Int,
    val downloadUrl: String,
    val originUrl: String,
    val isPrimary: Boolean,
    val order: Int
)

data class AlarmSettingForMyInfo(
    val alarmSettingId: Int,
    val allPush: Boolean,
    val lampInvite: Boolean,
    val newMatch: Boolean,
    val receiveBadge: Boolean,
    val receiveMessage: Boolean
)
