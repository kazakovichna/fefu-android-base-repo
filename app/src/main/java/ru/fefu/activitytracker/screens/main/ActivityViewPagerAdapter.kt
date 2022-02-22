package ru.fefu.activitytracker.screens.main

import androidx.fragment.app.Fragment
import ru.fefu.activitytracker.PagesDataViewPagerAdapter
import ru.fefu.activitytracker.R

class ActivityViewPagerAdapter(fragment: Fragment) : PagesDataViewPagerAdapter(fragment) {
    override val pagesData = listOf(
        FragmentPageData(fragment.getString(R.string.action_activity_my)) {
            UserActivityFragment.newInstance(isCurrentUser = true)
        },
        FragmentPageData(fragment.getString(R.string.action_activity_users)) {
            UserActivityFragment.newInstance(isCurrentUser = false)
        },
    )
}