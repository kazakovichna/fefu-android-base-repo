package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.view.View
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentActivityTabBinding

class ActivityTabFragment :
    BindingFragment<FragmentActivityTabBinding>(R.layout.fragment_activity_tab) {

    companion object {
        @JvmField
        val TAG = ActivityTabFragment::class.simpleName!!

        fun newInstance() = ActivityTabFragment()
    }

    override fun bind(view: View): FragmentActivityTabBinding =
        FragmentActivityTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(R.id.activity_fragment_container, ActivityFragment.newInstance())
                commit()
            }
        }
    }
}