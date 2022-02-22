package ru.fefu.activitytracker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class PagesDataViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    data class FragmentPageData(val title: String, val creator: () -> Fragment)

    abstract val pagesData: List<FragmentPageData>

    override fun getItemCount(): Int = pagesData.size

    override fun createFragment(position: Int): Fragment = pagesData[position].creator()
}