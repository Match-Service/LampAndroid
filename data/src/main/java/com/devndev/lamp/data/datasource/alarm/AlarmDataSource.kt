package com.devndev.lamp.data.datasource.alarm

import com.devndev.lamp.data.dto.response.alarm.AlarmResponse

interface AlarmDataSource {
    suspend fun getAlarm(): List<AlarmResponse>
}
