package com.devndev.lamp.domain.model

// todo 추후 Profile 정보 가져오도록 수정 필요
data class Item(
    val name: String,
    val isLampOn: Boolean,
    val roomName: String?
)
