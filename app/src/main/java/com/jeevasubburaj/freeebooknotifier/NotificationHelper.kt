package com.jeevasubburaj.freeebooknotifier

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.app.PendingIntent
import android.content.Intent

internal class NotificationHelper(ctx: Context) : ContextWrapper(ctx) {
    private val manager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    init {

        val chan = NotificationChannel(PRIMARY_CHANNEL,
                getString(R.string.noti_channel_default), NotificationManager.IMPORTANCE_DEFAULT)
        chan.lightColor = Color.GREEN
        chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manager.createNotificationChannel(chan)
    }

    fun getNotification(title: String, body: String): Notification.Builder {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        return Notification.Builder(applicationContext, PRIMARY_CHANNEL)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
    }

    fun notify(id: Int, notification: Notification.Builder) {
        manager.notify(id, notification.build())
    }

    private val smallIcon: Int
        get() = android.R.drawable.stat_notify_chat

    companion object {
        const val PRIMARY_CHANNEL = "default"
    }
}