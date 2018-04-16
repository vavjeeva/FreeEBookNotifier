package com.jeevasubburaj.freeebooknotifier

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.jeevasubburaj.freeebooknotifier.extensions.DelegatesExt
import com.jeevasubburaj.freeebooknotifier.utils.DEFAULT_REMINDER_TIME
import com.jeevasubburaj.freeebooknotifier.utils.REMINDER_TIME
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal class AlarmManagerHelper(ctx: Context) : ContextWrapper(ctx) {

    fun setBroadCastAlert(argRefreshAlarm: Boolean) {

        var refreshAlarm = argRefreshAlarm
        val alarmManager = this.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val notificationIntent = Intent(this.applicationContext, NotificationReceiver::class.java)
        notificationIntent.action = "com.jeevasubburaj.freeebooknotifier.alarm"

        var pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, notificationIntent, PendingIntent.FLAG_NO_CREATE)
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            refreshAlarm = true
        }
        if (refreshAlarm) {

            val formatter = SimpleDateFormat("HH:mm")
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            try {
                val reminderTime: String by DelegatesExt.preference(this, REMINDER_TIME, DEFAULT_REMINDER_TIME)
                val parseTime = formatter.parse(reminderTime)
                val timeCalendar = Calendar.getInstance()
                timeCalendar.time = parseTime
                calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR))
                calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
                calendar.set(Calendar.SECOND, 0)
            } catch (e: ParseException) {
                // This can happen if you are trying to parse an invalid date, e.g., 25:19:12.
                // Here, you should log the error and decide what to do next
                e.printStackTrace()

                //Set the default value
                calendar.set(Calendar.HOUR_OF_DAY, 9)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
            }
            alarmManager.cancel(pendingIntent)

            var finalCalInMillis: Long = calendar.timeInMillis
            if(calendar.timeInMillis <= System.currentTimeMillis())
                finalCalInMillis = calendar.timeInMillis + (AlarmManager.INTERVAL_DAY+1)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, finalCalInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }
}