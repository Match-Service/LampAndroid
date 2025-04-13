package com.devndev.lamp.data.dto.response.user

import com.devndev.lamp.domain.model.signup.ProfileImageDomainModel
import com.devndev.lamp.domain.model.user.AlarmSettingForMyInfo
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.model.user.ProfileImageForMyInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyInfoResponse(
    @Json(name = "userId") val userId: Int,
    @Json(name = "name") val name: String,
    @Json(name = "job") val job: String,
    @Json(name = "jobName") val jobName: String?,
    @Json(name = "gender") val gender: String,
    @Json(name = "birth") val birth: String,
    @Json(name = "instagramId") val instagramId: String?,
    @Json(name = "bio") val bio: String?,
    @Json(name = "profileImages") val profileImages: List<ProfileImage>,
    @Json(name = "alarmSetting") val alarmSetting: AlarmSetting,
    @Json(name = "bioQuestions") val bioQuestions: List<BioQuestion>
)

@JsonClass(generateAdapter = true)
data class ProfileImage(
    @Json(name = "profileImageId") val profileImageId: Int,
    @Json(name = "downloadUrl") val downloadUrl: String,
    @Json(name = "originUrl") val originUrl: String,
    @Json(name = "isPrimary") val isPrimary: Boolean,
    @Json(name = "order") val order: Int
)

fun ProfileImage.toDomainModel(): ProfileImageForMyInfo {
    return ProfileImageForMyInfo(
        profileImageId = profileImageId,
        downloadUrl = downloadUrl,
        originUrl = originUrl,
        isPrimary = isPrimary,
        order = order
    )
}

@JsonClass(generateAdapter = true)
data class AlarmSetting(
    @Json(name = "alarmSettingId") val alarmSettingId: Int,
    @Json(name = "allPush") val allPush: Boolean,
    @Json(name = "lampInvite") val lampInvite: Boolean,
    @Json(name = "lampVisit") val lampVisit: Boolean,
    @Json(name = "newMatch") val newMatch: Boolean,
    @Json(name = "receiveBadge") val receiveBadge: Boolean,
    @Json(name = "receiveMessage") val receiveMessage: Boolean
) {
    fun toDomainModel(): AlarmSettingForMyInfo {
        return AlarmSettingForMyInfo(
            alarmSettingId = alarmSettingId,
            allPush = allPush,
            lampInvite = lampInvite,
            lampVisit = lampVisit,
            newMatch = newMatch,
            receiveBadge = receiveBadge,
            receiveMessage = receiveMessage
        )
    }
}

@JsonClass(generateAdapter = true)
data class BioQuestion(
    @Json(name = "question") val question: String,
    @Json(name = "answer") val answer: String
) {
    fun toDomainModel(): com.devndev.lamp.domain.model.signup.BioQuestion {
        return com.devndev.lamp.domain.model.signup.BioQuestion(
            question = question,
            answer = answer
        )
    }
}

@JsonClass(generateAdapter = true)
data class editProfileImageResponse(
    @Json(name = "imageUrl")
    val imageUrl: String
)

fun MyInfoResponse.toDomainModel(): MyInfoDomainModel {
    return MyInfoDomainModel(
        userId = userId,
        name = name,
        job = job,
        jobName = jobName,
        gender = gender,
        birth = birth,
        instagramId = instagramId,
        bio = bio,
        profileImages = profileImages.map { it.toDomainModel() },
        alarmSetting = alarmSetting.toDomainModel(),
        bioQuestions = bioQuestions.map { it.toDomainModel() }
    )
}

fun editProfileImageResponse.toDomainModel(): ProfileImageDomainModel {
    return ProfileImageDomainModel(
        imageUrl = imageUrl
    )
}

fun List<editProfileImageResponse>.toDomainModel(): List<ProfileImageDomainModel> {
    return map { it.toDomainModel() }
}
