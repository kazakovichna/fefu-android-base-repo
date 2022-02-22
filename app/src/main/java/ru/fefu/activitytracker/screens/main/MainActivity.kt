package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.fefu.activitytracker.BottomNavigationManager
import ru.fefu.activitytracker.BottomNavigationManager.NavigationItem
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private val navigationItems = listOf(
            NavigationItem(
                R.id.action_activity,
                ActivityTabFragment.TAG,
            ) { ActivityTabFragment.newInstance() },
            NavigationItem(
                R.id.action_profile,
                ProfileTabFragment.TAG,
            ) { ProfileTabFragment.newInstance() },
        )
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var navigationManager: BottomNavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeNavigationHandler(savedInstanceState)
    }

    override fun onDestroy() {
        navigationManager.dispose()
        super.onDestroy()
    }

    private fun initializeNavigationHandler(savedInstanceState: Bundle?) {
        navigationManager = BottomNavigationManager(
            fragmentManager = supportFragmentManager,
            navigationView = binding.navigation,
            fragmentContainerId = R.id.fragment_container,
            mainNavigationItemId = navigationItems.first().id,
            navigationItems = navigationItems,
            savedInstanceState = savedInstanceState,
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navigationManager.saveState(outState)
    }

    override fun onBackPressed() {
        val currentTabFragmentManager = navigationManager.currentFragment!!.childFragmentManager

        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else if (currentTabFragmentManager.backStackEntryCount != 0) {
            currentTabFragmentManager.popBackStack()
        } else if (!navigationManager.handleBackPressed()) {
            super.onBackPressed()
        }
    }
}