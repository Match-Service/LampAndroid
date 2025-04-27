package com.devndev.lamp.data.dto.response.chat

import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.Individuality
import com.devndev.lamp.domain.model.chat.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatInfoResponse(
    @Json(name = "startDate")
    val startDate: String,
    @Json(name = "inviteUserCount")
    val inviteUserCount: Int,
    @Json(name = "myLampName")
    val myLampName: String,
    @Json(name = "myLampId")
    val myLampId: Int,
    @Json(name = "otherLampName")
    val otherLampName: String,
    @Json(name = "otherLampId")
    val otherLampId: Int,
    @Json(name = "userInfos")
    val userInfos: List<UserInfoResponse>
)

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "job")
    val job: String?,
    @Json(name = "jobName")
    val jobName: String?,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "birth")
    val birth: String,
    @Json(name = "instagramId")
    val instagramId: String?,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "profileImages")
    val profileImages: List<String>,
    @Json(name = "individuality")
    val individuality: IndividualityResponse
)

@JsonClass(generateAdapter = true)
data class IndividualityResponse(
    @Json(name = "attractiveness")
    val attractiveness: Int,
    @Json(name = "personality")
    val personality: Int,
    @Json(name = "voice")
    val voice: Int,
    @Json(name = "fashion")
    val fashion: Int,
    @Json(name = "conversation")
    val conversation: Int
)

fun ChatInfoResponse.toDomainModel(): ChatInfoDomainModel {
    return ChatInfoDomainModel(
        startDate = startDate,
        inviteUserCount = inviteUserCount,
        myLampName = myLampName,
        myLampId = myLampId,
        otherLampName = otherLampName,
        otherLampId = otherLampId,
        userInfos = userInfos.map { it.toDomainModel() }
    )
}

fun UserInfoResponse.toDomainModel(): UserInfo {
    return UserInfo(
        userId = userId,
        name = name,
        job = job,
        jobName = jobName,
        gender = gender,
        birth = birth,
        instagramId = instagramId,
        bio = bio,
        profileImages = profileImages,
        individuality = individuality.toDomainModel()
    )
}

fun IndividualityResponse.toDomainModel(): Individuality {
    return Individuality(
        attractiveness = attractiveness,
        personality = personality,
        voice = voice,
        fashion = fashion,
        conversation = conversation
    )
}
