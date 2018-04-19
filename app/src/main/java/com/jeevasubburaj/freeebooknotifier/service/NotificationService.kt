package com.jeevasubburaj.freeebooknotifier.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import com.jeevasubburaj.freeebooknotifier.MainActivity
import com.jeevasubburaj.freeebooknotifier.NotificationHelper
import com.jeevasubburaj.freeebooknotifier.ParserHelper
import com.jeevasubburaj.freeebooknotifier.R


class NotificationService : JobIntentService() {

    private lateinit var notiHelper: NotificationHelper
    private lateinit var parserHelper: ParserHelper

    companion object {
        private const val JOB_ID = 1000
        private const val NOTI_PRIMARY = 1100

        fun enqueueWork(ctx: Context, intent: Intent) {
            enqueueWork(ctx, NotificationService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {

        notiHelper = NotificationHelper(this)
        parserHelper = ParserHelper(this);

        val(title) = parserHelper.parsePacktPubFreeBook()

        val resultIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0)

        notiHelper.notify(NOTI_PRIMARY, notiHelper.getNotification(getString(R.string.noti_title), title,pendingIntent))
    }
}