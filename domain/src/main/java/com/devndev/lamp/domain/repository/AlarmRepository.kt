package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.alarm.AlarmDomainModel

interface AlarmRepository {
    suspend fun getAlarm(): List<AlarmDomainModel>
}
