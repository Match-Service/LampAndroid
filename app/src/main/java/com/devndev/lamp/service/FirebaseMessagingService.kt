package com.devndev.lamp.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.devndev.lamp.domain.eventbus.AlarmEventBus
import com.devndev.lamp.presentation.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseMessagingService : FirebaseMessagingService() {
    private val logTag = "FCMService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(logTag, "onMessageReceived")

        if (remoteMessage.data["messageType"] == "INVITE_REQUEST" || remoteMessage.data["messageType"] == "VISIT_REQUEST") {
            CoroutineScope(Dispatchers.Default).launch {
                AlarmEventBus.postEvent()
            }
        }

        with(remoteMessage) {
            sendNotification(data["title"], data["message"], data["messageType"])
        }
    }

    @SuppressLint("ServiceCast", "MissingPermission")
    private fun sendNotification(title: String?, body: String?, messageType: String?) {
        // 알림 채널 생성
        val channelId = "lamp_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            "lamp alarm",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("navigate_target", messageType)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            messageType.hashCode(), // messageType 기준으로 requestCode 고유화
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 표시
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(com.devndev.lamp.presentation.R.drawable.alarm_icon)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(0, notification)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FirebaseMessagingService", "newToken: $token")
    }
}
