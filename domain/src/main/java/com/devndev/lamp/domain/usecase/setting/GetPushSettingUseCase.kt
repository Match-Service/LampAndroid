package com.devndev.lamp.domain.usecase.setting

import com.devndev.lamp.domain.model.setting.PushSettingDomainModel
import com.devndev.lamp.domain.repository.SettingRepository
import javax.inject.Inject

class GetPushSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(): Result<PushSettingDomainModel> {
        return runCatching {
            settingRepository.getPushSetting()
        }
    }
}
