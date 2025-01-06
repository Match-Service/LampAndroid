package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.FcmNotificationParam

interface NotificationRepository {
    suspend fun sendFcmNotification(fcmNotificationParam: FcmNotificationParam)
}
