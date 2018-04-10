package com.jeevasubburaj.freeebooknotifier

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.util.Log
import org.jsoup.Jsoup

internal class ParserHelper(ctx: Context) : ContextWrapper(ctx) {

    private val tag: String = "ParserHelper"

    data class ParserEntity(val title: String, val imageURL: String?)

    fun parsePacktPubFreeBook(): ParserEntity {
        var title = "Internet access not available"
        var imageURL: String? = null
        if (isInternetConnected()) {
            try {
                val htmlContent = Jsoup.connect(getString(R.string.FREE_BOOK_URL)).get()
                title = htmlContent.select("div[class=dotd-title]").text()
                imageURL = "http:" + htmlContent.select("img[class=bookimage imagecache imagecache-dotd_main_image]").attr("src")
            } catch (e: Exception) {
                Log.e(tag, "Error in fetching the content " + e.printStackTrace())
            }
        }

        return ParserEntity(title, imageURL)
    }

    private fun isInternetConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}

