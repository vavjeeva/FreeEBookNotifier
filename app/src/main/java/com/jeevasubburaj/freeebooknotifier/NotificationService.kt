package com.jeevasubburaj.freeebooknotifier

import android.app.IntentService
import android.content.Intent
import org.jsoup.Jsoup

class NotificationService : IntentService("NotificationService") {

    private lateinit var helper: NotificationHelper

    companion object {
        const val NOTI_PRIMARY = 1001
    }

    override fun onHandleIntent(intent: Intent?) {
        helper = NotificationHelper(this)
        val htmlContent = Jsoup.connect(getString(R.string.FREE_BOOK_URL)).get()
        val title = "Free Book of the Day"
        val message = htmlContent.select("div[class=dotd-title]").text()
        helper.notify(NOTI_PRIMARY, helper.getNotification(title, message))
    }
}