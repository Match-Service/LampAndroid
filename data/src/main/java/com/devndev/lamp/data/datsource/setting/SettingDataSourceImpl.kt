package com.devndev.lamp.data.datsource.setting

import com.devndev.lamp.data.dto.request.setting.PushSettingRequest
import com.devndev.lamp.data.dto.response.setting.PushSettingResponse
import com.devndev.lamp.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl @Inject constructor(
    private val settingService: SettingService
) : SettingDataSource {
    override suspend fun getPushSetting(): PushSettingResponse {
        return settingService.getPushSetting()
    }

    override suspend fun putPushSetting(pushSettingRequest: PushSettingRequest) {
        settingService.putPushSetting(pushSettingRequest)
    }
}
