package com.jeevasubburaj.freeebooknotifier

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import java.util.*

internal class AlarmManagerHelper(ctx: Context) : ContextWrapper(ctx) {

    fun setBroadCastAlert(argRefreshAlarm: Boolean) {

        var refreshAlarm = argRefreshAlarm
        val alarmManager = this.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this.applicationContext, NotificationReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        var pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE)
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            refreshAlarm = true
        }
        if (refreshAlarm)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}