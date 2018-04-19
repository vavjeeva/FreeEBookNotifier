package com.jeevasubburaj.freeebooknotifier

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.jeevasubburaj.freeebooknotifier.extensions.DelegatesExt
import com.jeevasubburaj.freeebooknotifier.utils.Constants

internal class AlarmManagerHelper(ctx: Context) : ContextWrapper(ctx) {

    fun setBroadCastAlert(argRefreshAlarm: Boolean) {

        var refreshAlarm = argRefreshAlarm
        val alarmManager = this.getSystemService(Activity.ALARM_SERVICE) as AlarmManager

        val notificationIntent = Intent(Constants.ALARM_RECEIVER_INTENT_TRIGGER)
        notificationIntent.setClass(this, NotificationReceiver::class.java)

        var pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE)
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            refreshAlarm = true
        }
        if (refreshAlarm) {
            val syncFrequency: String by DelegatesExt.preference(this, Constants.SYNC_FREQUENCY_NAME, Constants.SYNC_FREQUENCY_DEFAULT_VALUE)
            val syncFrequencyLong = syncFrequency.toLong()
            var finalCalInMillis: Long = System.currentTimeMillis() + (syncFrequencyLong * AlarmManager.INTERVAL_HOUR)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, finalCalInMillis, AlarmManager.INTERVAL_HOUR , pendingIntent)
        }
    }
}