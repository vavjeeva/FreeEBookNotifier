package com.jeevasubburaj.freeebooknotifier

import android.support.v7.widget.Toolbar
import android.util.Log
import org.jetbrains.anko.startActivity

interface ToolbarManager {

    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun initToolbar() {
        toolbar.inflateMenu(R.menu.action_bar)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolbar.context.startActivity<SettingsActivity>()
                else -> Log.i("TOOLBAR","Unknown Option Selected")
            }
            true
        }
    }
}