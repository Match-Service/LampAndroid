package com.devndev.lamp.data.datsource.alarm

import com.devndev.lamp.data.dto.response.alarm.AlarmResponse

interface AlarmDataSource {
    suspend fun getAlarm(): List<AlarmResponse>
    suspend fun deleteAlarm(alarmId: Int)
}
