package com.zed.kz.doskaz.global.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object Utils {
    fun createNotificationChannel(
        channelId: String?,
        channelName: String?,
        context: Context
    ) {
        val notificationManager = getNotificationManager(context)
            ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun deleteNotificationChannel(channelId: String?, context: Context) {
        val notificationManager = getNotificationManager(context)
            ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.deleteNotificationChannel(channelId)
        }
    }

    fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}