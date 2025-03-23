package com.devndev.lamp.data.dto.response.lamp

import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.devndev.lamp.domain.model.lamp.Lamp as DomainLamp
import com.devndev.lamp.domain.model.lamp.Owner as DomainOwner
import com.devndev.lamp.domain.model.lamp.Participant as DomainParticipant

@JsonClass(generateAdapter = true)
data class LampResponse(
    @Json(name = "lamp")
    val lamp: Lamp?
) {
    fun toDomainModel(): LampDomainModel {
        return LampDomainModel(
            lamp = lamp?.toDomainModel()
        )
    }
}

@JsonClass(generateAdapter = true)
data class Lamp(
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "hopeMatchNumber")
    val hopeMatchNumber: Int,
    @Json(name = "location")
    val location: String,
    @Json(name = "color")
    val color: String,
    @Json(name = "lampStatus")
    val lampStatus: String,
    @Json(name = "owner")
    val owner: Owner,
    @Json(name = "participants")
    val participants: List<Participant>
) {
    fun toDomainModel(): DomainLamp {
        return DomainLamp(
            lampId = lampId,
            name = name,
            description = description,
            url = url,
            hopeMatchNumber = hopeMatchNumber,
            location = location,
            color = color,
            lampStatus = lampStatus,
            owner = owner.toDomainModel(),
            participants = participants.map { it.toDomainModel() }
        )
    }
}

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String
) {
    fun toDomainModel(): DomainOwner {
        return DomainOwner(
            userId = userId,
            name = name,
            profileImageUrl = profileImageUrl
        )
    }
}

@JsonClass(generateAdapter = true)
data class Participant(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "profileImageUrl")
    val profileImageUrl: String
) {
    fun toDomainModel(): DomainParticipant {
        return DomainParticipant(
            userId = userId,
            name = name,
            profileImageUrl = profileImageUrl
        )
    }
}
