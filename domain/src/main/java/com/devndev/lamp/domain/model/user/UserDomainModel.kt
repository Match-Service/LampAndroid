package com.devndev.lamp.domain.model.user

data class UserDomainModel(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val lampId: Int?,
    val lampStatus: String
)
