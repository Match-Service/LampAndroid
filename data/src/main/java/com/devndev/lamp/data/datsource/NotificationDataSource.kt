package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.FcmNotificationRequest
import retrofit2.Response

interface NotificationDataSource {
    suspend fun sendFcmNotification(fcmNotificationRequest: FcmNotificationRequest): Response<Void>
}
