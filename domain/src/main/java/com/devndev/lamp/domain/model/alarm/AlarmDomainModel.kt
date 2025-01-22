package com.devndev.lamp.domain.model.alarm

data class AlarmDomainModel(
    val type: String,
    val content: String,
    val lampId: Int,
    val createdAt: String
)
