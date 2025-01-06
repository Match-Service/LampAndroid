package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.FcmNotificationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {
    @POST("/api/v1/test/push")
    suspend fun sendFcmNotification(
        @Body fcmNotificationRequest: FcmNotificationRequest
    ): Response<Void>
}
