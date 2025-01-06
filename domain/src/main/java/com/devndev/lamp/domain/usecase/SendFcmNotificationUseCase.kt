package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.FcmNotificationParam
import com.devndev.lamp.domain.repository.NotificationRepository
import javax.inject.Inject

class SendFcmNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(fcmNotificationParam: FcmNotificationParam) {
        notificationRepository.sendFcmNotification(fcmNotificationParam)
    }
}
