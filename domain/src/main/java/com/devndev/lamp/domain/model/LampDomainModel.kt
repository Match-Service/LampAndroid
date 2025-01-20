package com.devndev.lamp.domain.model

data class LampDomainModel(
    val lamp: Lamp?
)

data class Lamp(
    val lampId: Int,
    val name: String,
    val description: String,
    val url: String,
    val hopeMatchNumber: Int,
    val location: String,
    val color: String,
    val owner: Owner,
    val participants: List<Participant>
)

data class Owner(
    val userId: Int,
    val name: String,
    val profileImageUrl: String
)

data class Participant(
    val userId: Int,
    val name: String,
    val profileImageUrl: String
)
