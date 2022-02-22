package ru.fefu.activitytracker.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentActivityBinding
import ru.fefu.activitytracker.screens.new_activity.NewActivityActivity

class ActivityFragment : BindingFragment<FragmentActivityBinding>(R.layout.fragment_activity) {
    companion object {
        fun newInstance() = ActivityFragment()
    }

    private lateinit var pagerAdapter: ActivityViewPagerAdapter

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun bind(view: View): FragmentActivityBinding = FragmentActivityBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = ActivityViewPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = pagerAdapter.pagesData[position].title
        }.apply(TabLayoutMediator::attach)
        setUpButtonsHandlers()
    }

    override fun onDestroy() {
        tabLayoutMediator.detach()
        super.onDestroy()
    }

    private fun setUpButtonsHandlers() {
        binding.fab.setOnClickListener {
            val intent = Intent(requireContext(), NewActivityActivity::class.java)
            startActivity(intent)
        }
    }
}