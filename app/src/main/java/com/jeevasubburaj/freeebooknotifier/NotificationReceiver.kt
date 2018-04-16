package com.jeevasubburaj.freeebooknotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action.equals("com.jeevasubburaj.freeebooknotifier.alarm",true)) {
            val service = Intent(context, NotificationService::class.java)
            context.startService(service)
        }
    }

}