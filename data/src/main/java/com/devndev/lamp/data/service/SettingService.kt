package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.setting.PushSettingRequest
import com.devndev.lamp.data.dto.response.setting.PushSettingResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface SettingService {
    @GET("api/v1/user/me/alarm-setting")
    suspend fun getPushSetting(): PushSettingResponse

    @PUT("api/v1/user/me/alarm-setting")
    suspend fun putPushSetting(
        @Body pushSettingRequest: PushSettingRequest
    )
}
