package com.jeevasubburaj.freeebooknotifier

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


class BootReceiver : BroadcastReceiver() {

    private lateinit var alarmManagerHelper: AlarmManagerHelper

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            alarmManagerHelper = AlarmManagerHelper(context)
            alarmManagerHelper.setBroadCastAlert(false)
        }
    }
}