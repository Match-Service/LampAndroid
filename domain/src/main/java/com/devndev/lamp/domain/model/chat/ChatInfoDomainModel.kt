package com.devndev.lamp.domain.model.chat

data class ChatInfoDomainModel(
    val startDate: String,
    val inviteUserCount: Int,
    val myLampName: String,
    val myLampId: Int,
    val otherLampName: String,
    val otherLampId: Int,
    val userInfos: List<UserInfo>
)

data class UserInfo(
    val userId: Int,
    val name: String,
    val job: String?,
    val jobName: String?,
    val gender: String,
    val birth: String,
    val instagramId: String?,
    val bio: String?,
    val profileImages: List<String>,
    val individuality: Individuality
)

data class Individuality(
    val attractiveness: Int,
    val personality: Int,
    val voice: Int,
    val fashion: Int,
    val conversation: Int
)
