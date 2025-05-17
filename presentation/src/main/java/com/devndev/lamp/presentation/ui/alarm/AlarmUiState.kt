package com.devndev.lamp.presentation.ui.alarm

import com.devndev.lamp.domain.model.alarm.AlarmDomainModel

data class AlarmUiState(
    val alarms: List<AlarmDomainModel> = emptyList(),
    val alarmExist: Boolean = false
)