package com.devndev.lamp.domain.usecase.alarm

import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.devndev.lamp.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(): List<AlarmDomainModel> {
        return alarmRepository.getAlarm()
    }
}
