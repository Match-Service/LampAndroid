package com.devndev.lamp.domain.model.alarm

data class AlarmDomainModel(
    val id: Int,
    val type: String,
    val content: String,
    val lampId: Int,
    val inviteUserId: Int?,
    val createdAt: String
)
