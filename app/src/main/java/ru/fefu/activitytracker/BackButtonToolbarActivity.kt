package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity

open class BackButtonToolbarActivity : AppCompatActivity() {
    protected fun setToolbar() {
        setSupportActionBar(findViewById(R.id.back_button_toolbar))
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}