package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.notification.NotificationDataSource
import com.devndev.lamp.data.dto.request.notification.FcmNotificationRequest
import com.devndev.lamp.domain.model.notification.FcmNotificationParam
import com.devndev.lamp.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun sendFcmNotification(fcmNotificationParam: FcmNotificationParam) {
        val fcmNotificationRequest = FcmNotificationRequest(
            pushToken = fcmNotificationParam.pushToken,
            title = fcmNotificationParam.title,
            message = fcmNotificationParam.message
        )
        notificationDataSource.sendFcmNotification(fcmNotificationRequest)
    }
}
