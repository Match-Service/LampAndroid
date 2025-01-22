package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.alarm.AlarmDataSource
import com.devndev.lamp.data.dto.response.alarm.toDomainModel
import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.devndev.lamp.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDataSource: AlarmDataSource
) : AlarmRepository {
    override suspend fun getAlarm(): List<AlarmDomainModel> {
        return alarmDataSource.getAlarm().toDomainModel()
    }
}
