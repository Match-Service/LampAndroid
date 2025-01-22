package com.devndev.lamp.data.datsource.alarm

import com.devndev.lamp.data.dto.response.alarm.AlarmResponse
import com.devndev.lamp.data.service.AlarmService
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(
    private val alarmService: AlarmService
) : AlarmDataSource {
    override suspend fun getAlarm(): List<AlarmResponse> {
        return alarmService.getAlarm()
    }
}
