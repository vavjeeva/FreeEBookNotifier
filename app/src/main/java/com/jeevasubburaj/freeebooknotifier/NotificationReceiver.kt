package com.jeevasubburaj.freeebooknotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jeevasubburaj.freeebooknotifier.service.NotificationService

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationService.enqueueWork(context, intent)
    }

}