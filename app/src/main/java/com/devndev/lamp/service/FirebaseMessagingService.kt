package com.devndev.lamp.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    // fcm에서 메시지 받아서 처리하는 부분
    // TODO: 알람별로 data 파싱해서 처리하면 될듯
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.data["title"] ?: "알림"
        val body = remoteMessage.data["body"] ?: "초대 메시지가 도착했습니다"
        sendNotification(title, body)
    }

    @SuppressLint("ServiceCast", "MissingPermission")
    private fun sendNotification(title: String, body: String) {
        // 알림 채널 생성
        val channelId = "invite_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "초대 알림",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 표시
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(com.devndev.lamp.presentation.R.drawable.alarm_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(this).notify(0, notification)
    }
}
