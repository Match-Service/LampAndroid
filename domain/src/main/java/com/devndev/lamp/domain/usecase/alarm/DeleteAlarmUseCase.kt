package com.devndev.lamp.domain.usecase.alarm

import com.devndev.lamp.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarmId: Int): Result<Unit> {
        return runCatching {
            alarmRepository.deleteAlarm(alarmId)
        }
    }
}
