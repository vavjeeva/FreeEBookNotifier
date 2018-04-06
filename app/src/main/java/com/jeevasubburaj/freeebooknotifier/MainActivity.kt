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


class MainActivity : AppCompatActivity() {

    private val Tag: String = "ParseEbookTask"

    var mImageURL: String? = null
    var mTitle: String? = null
    var mDesc: String? = null

    lateinit var mImageView: ImageView
    lateinit var mTitleView: TextView
    lateinit var mClaimButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageView = findViewById(R.id.imageView)
        mTitleView = findViewById(R.id.title)
        mClaimButton = findViewById(R.id.claimThisBook)
        mClaimButton.setOnClickListener{
            launchBookURL()
        }

        //Call the Async Task
        ParseEbookTask().execute()
    }

    fun launchBookURL() {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.packtpub.com/packt/offers/free-learning"))
        startActivity(intent)
    }

    inner class ParseEbookTask() : AsyncTask<Unit, Unit, Unit>() {


        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: Unit) {

            //It will return current data and time.
            val url = "https://www.packtpub.com/packt/offers/free-learning";

            try {
                val mEbookDocument = Jsoup.connect(url).get()
                mImageURL = "http:" + mEbookDocument.select("img[class=bookimage imagecache imagecache-dotd_main_image]").attr("src")
                mTitle = mEbookDocument.select("div[class=dotd-title]").text()
                //Log.e(Tag, "Image URL " + mImageURL)

            } catch (e: IOException) {
                Log.e(Tag, "Error in doInBackground " + e.printStackTrace())
            }
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            Glide.with(this@MainActivity).load(mImageURL).into(mImageView)
            mTitleView.setText(mTitle)
        }
    }
}
