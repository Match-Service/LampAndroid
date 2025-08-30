package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.response.alarm.AlarmResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface AlarmService {
    @GET("api/v1/alarm")
    suspend fun getAlarm(): List<AlarmResponse>

    @DELETE("api/v1/alarm/{alarmId}")
    suspend fun deleteAlarm(
        @Path("alarmId") alarmId: Int
    )
}
