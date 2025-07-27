package com.devndev.lamp.domain.usecase.setting

import com.devndev.lamp.domain.model.setting.PushSettingParam
import com.devndev.lamp.domain.repository.SettingRepository
import javax.inject.Inject

class PutPushSettingUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    suspend operator fun invoke(pushSettingParam: PushSettingParam): Result<Unit> {
        return runCatching {
            settingRepository.putPushSetting(pushSettingParam)
        }
    }
}
