package com.devndev.lamp.domain.model.lamp

data class CreateLampParam(
    val name: String,
    val description: String,
    val hopeMatchNumber: Int,
    val location: String,
    val color: String
)
