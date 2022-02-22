package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.view.View
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileTabBinding

class ProfileTabFragment :
    BindingFragment<FragmentProfileTabBinding>(R.layout.fragment_profile_tab) {

    companion object {
        @JvmField
        val TAG = ProfileTabFragment::class.simpleName!!

        fun newInstance() = ProfileTabFragment()
    }

    override fun bind(view: View): FragmentProfileTabBinding =
        FragmentProfileTabBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction().apply {
                add(R.id.profile_fragment_container, ProfileFragment.newInstance())
                commit()
            }
        }
    }
}