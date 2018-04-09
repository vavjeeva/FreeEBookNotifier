package com.jeevasubburaj.freeebooknotifier

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private val Tag: String = "ParseHtmlTask"
    var mImageURL: String? = null
    var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        claimThisBook.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(getString(R.string.FREE_BOOK_URL)))
            startActivity(intent)
        }

        val alarmManager = this.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this.applicationContext, NotificationReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 15)
        calendar.set(Calendar.MINUTE, 20)

        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        //Call the Async Task
        ParseHtmlTask().execute()
    }

    inner class ParseHtmlTask() : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit) {
            try {
                val htmlContent = Jsoup.connect(getString(R.string.FREE_BOOK_URL)).get()
                mImageURL = "http:" + htmlContent.select("img[class=bookimage imagecache imagecache-dotd_main_image]").attr("src")
                mTitle = htmlContent.select("div[class=dotd-title]").text()

            } catch (e: IOException) {
                Log.e(Tag, "Error in doInBackground " + e.printStackTrace())
            }
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            Glide.with(this@MainActivity).load(mImageURL).into(imageView)
            titleView.text = mTitle
        }
    }
}
