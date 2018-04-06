package com.jeevasubburaj.freeebooknotifier

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
