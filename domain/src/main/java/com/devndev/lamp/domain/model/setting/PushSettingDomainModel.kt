package com.devndev.lamp.domain.model.setting

data class PushSettingDomainModel(
    val allPush: Boolean,
    val lampInvite: Boolean,
    val lampVisit: Boolean,
    val newMatch: Boolean,
    val receiveAssessment: Boolean,
    val receiveMessage: Boolean
)
