package com.devndev.lamp.domain.model

data class UserDomainModel(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val lampId: Int?
)
