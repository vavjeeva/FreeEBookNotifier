package com.jeevasubburaj.freeebooknotifier

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.jeevasubburaj.freeebooknotifier.extensions.DelegatesExt
import com.jeevasubburaj.freeebooknotifier.utils.Constants
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var syncFrequency: String by DelegatesExt.preference(this, Constants.SYNC_FREQUENCY_NAME, Constants.SYNC_FREQUENCY_DEFAULT_VALUE)
    private lateinit var alarmManagerHelper: AlarmManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ArrayAdapter.createFromResource(this,
                R.array.pref_sync_frequency_titles, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        syncFreqSpinner.adapter = adapter
        syncFreqSpinner.onItemSelectedListener = this

        syncFreqSpinner.setSelection(resources.getStringArray(R.array.pref_sync_frequency_values).indexOf(syncFrequency))
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View,
                                pos: Int, id: Long) {
        syncFrequency = resources.getStringArray(R.array.pref_sync_frequency_values)[parent.selectedItemPosition]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
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