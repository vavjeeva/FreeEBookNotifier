package com.jeevasubburaj.freeebooknotifier

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jeevasubburaj.freeebooknotifier.extensions.DelegatesExt
import com.jeevasubburaj.freeebooknotifier.utils.DEFAULT_REMINDER_TIME
import com.jeevasubburaj.freeebooknotifier.utils.REMINDER_TIME
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity() {

    private var reminderTime: String by DelegatesExt.preference(this, REMINDER_TIME, DEFAULT_REMINDER_TIME)
    private lateinit var alarmManagerHelper: AlarmManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        reminderTimeView.setText(reminderTime)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        reminderTime = reminderTimeView.text.toString()
        alarmManagerHelper = AlarmManagerHelper(this)
        alarmManagerHelper.setBroadCastAlert(true)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }
}