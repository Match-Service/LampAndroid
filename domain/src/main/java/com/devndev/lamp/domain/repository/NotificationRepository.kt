package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.notification.FcmNotificationParam

interface NotificationRepository {
    suspend fun sendFcmNotification(fcmNotificationParam: FcmNotificationParam)
}
