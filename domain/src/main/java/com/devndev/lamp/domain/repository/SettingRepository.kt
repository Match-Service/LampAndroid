package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.setting.PushSettingDomainModel
import com.devndev.lamp.domain.model.setting.PushSettingParam

interface SettingRepository {
    suspend fun getPushSetting(): PushSettingDomainModel
    suspend fun putPushSetting(pushSettingParam: PushSettingParam)
}
