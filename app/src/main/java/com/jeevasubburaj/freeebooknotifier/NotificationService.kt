package com.jeevasubburaj.freeebooknotifier

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import org.jsoup.Jsoup


class NotificationService : IntentService("NotificationService") {

    private lateinit var notiHelper: NotificationHelper
    private lateinit var parserHelper: ParserHelper

    companion object {
        const val NOTI_PRIMARY = 1001
    }

    override fun onHandleIntent(intent: Intent?) {
        notiHelper = NotificationHelper(this)
        parserHelper = ParserHelper(this);

        val(title) = parserHelper.parsePacktPubFreeBook()
        notiHelper.notify(NOTI_PRIMARY, notiHelper.getNotification(getString(R.string.noti_title), title))

    }
}