package com.jeevasubburaj.freeebooknotifier

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.bumptech.glide.Glide
import com.jeevasubburaj.freeebooknotifier.R.string.app_name
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find


class MainActivity : AppCompatActivity(),ToolbarManager {

    private val Tag: String = "MainActivity"
    private lateinit var parserHelper: ParserHelper
    private lateinit var alarmManagerHelper: AlarmManagerHelper
    var mImageURL: String? = null
    var mTitle: String? = null
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        toolbarTitle = getString(app_name)

        claimThisBook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(getString(R.string.FREE_BOOK_URL)))
            startActivity(intent)
        }

        //Call the Async Task
        LoadContentTask().execute()

        //Setting the Broadcast Alert
        alarmManagerHelper = AlarmManagerHelper(this)
        alarmManagerHelper.setBroadCastAlert(false)

        //Enable the BootReceiver
        enableBootReceiver()
    }

    private fun enableBootReceiver() {
        val receiver = ComponentName(this, BootReceiver::class.java)
        val pm = this.packageManager
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

    inner class LoadContentTask : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit) {
            parserHelper = ParserHelper(this@MainActivity);
            val (title, imageURL) = parserHelper.parsePacktPubFreeBook()
            mTitle = title
            mImageURL = imageURL
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            Glide.with(this@MainActivity).load(mImageURL).into(imageView)
            titleView.text = mTitle
        }
    }
}
