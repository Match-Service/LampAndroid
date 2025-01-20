package com.devndev.lamp.data.datsource.notification

import com.devndev.lamp.data.dto.request.notification.FcmNotificationRequest
import retrofit2.Response

interface NotificationDataSource {
    suspend fun sendFcmNotification(fcmNotificationRequest: FcmNotificationRequest): Response<Void>
}
