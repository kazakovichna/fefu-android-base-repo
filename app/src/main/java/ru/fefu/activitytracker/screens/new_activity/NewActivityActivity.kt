package ru.fefu.activitytracker.screens.new_activity

import android.os.Bundle
import ru.fefu.activitytracker.BackButtonToolbarActivity
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityNewActivityBinding

class NewActivityActivity : BackButtonToolbarActivity() {
    private lateinit var binding: ActivityNewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        initializeBottomMenu(savedInstanceState)
    }

    private fun initializeBottomMenu(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(
                    R.id.new_activity_menu_fragment_container,
                    NewActivityStartFragment.newInstance(),
                )
                commit()
            }
        }
    }

}