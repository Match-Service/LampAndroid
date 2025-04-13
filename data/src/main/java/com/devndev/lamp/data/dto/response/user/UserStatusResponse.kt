package com.devndev.lamp.data.dto.response.user

import com.devndev.lamp.domain.model.user.UserStatusDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserStatusResponse(
    @Json(name = "userLampStatus")
    val userLampStatus: String
) {
    fun toDomainModel(): UserStatusDomainModel {
        return UserStatusDomainModel(
            userLampStatus = userLampStatus
        )
    }
}
