package com.devndev.lamp.domain.model

data class FcmNotificationParam(
    val pushToken: String,
    val title: String,
    val message: String
)
