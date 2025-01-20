package com.devndev.lamp.data.datsource.notification

import android.util.Log
import com.devndev.lamp.data.dto.request.notification.FcmNotificationRequest
import com.devndev.lamp.data.service.NotificationService
import retrofit2.Response
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationDataSource {
    override suspend fun sendFcmNotification(fcmNotificationRequest: FcmNotificationRequest): Response<Void> {
        val response = notificationService.sendFcmNotification(fcmNotificationRequest)
        if (response.isSuccessful) {
            Log.d("pushAlarm", "Success: ${response.code()} - ${response.message()}")
        } else {
            Log.e("pushAlarm", "Failed: ${response.code()} - ${response.errorBody()?.string()}")
        }
        return response
    }
}
