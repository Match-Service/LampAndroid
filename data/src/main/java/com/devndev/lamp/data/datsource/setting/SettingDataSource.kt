package com.devndev.lamp.data.datsource.setting

import com.devndev.lamp.data.dto.request.setting.PushSettingRequest
import com.devndev.lamp.data.dto.response.setting.PushSettingResponse

interface SettingDataSource {
    suspend fun getPushSetting(): PushSettingResponse
    suspend fun putPushSetting(pushSettingRequest: PushSettingRequest)
}
