package com.jeevasubburaj.freeebooknotifier

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jsoup.Jsoup
import org.w3c.dom.Text
import java.io.IOException
import android.content.Intent
import android.net.Uri
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val Tag: String = "ParseEbookTask"

    var mImageURL: String? = null
    var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        claimThisBook.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("https://www.packtpub.com/packt/offers/free-learning"))
            startActivity(intent)
        }

        //Call the Async Task
        ParseEbookTask().execute()
    }

    inner class ParseEbookTask() : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit) {

            //It will return current data and time.
            val url = "https://www.packtpub.com/packt/offers/free-learning";

            try {
                val mEbookDocument = Jsoup.connect(url).get()
                mImageURL = "http:" + mEbookDocument.select("img[class=bookimage imagecache imagecache-dotd_main_image]").attr("src")
                mTitle = mEbookDocument.select("div[class=dotd-title]").text()

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
