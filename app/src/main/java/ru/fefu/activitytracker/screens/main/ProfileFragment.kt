package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentProfileBinding

class ProfileFragment : BindingFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun bind(view: View): FragmentProfileBinding = FragmentProfileBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpButtonHandlers()
    }

    private fun setUpToolbar() {
        with(binding.toolbar) {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_save_profile -> {
                        Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setUpButtonHandlers() {
        binding.changePasswordButton.setOnClickListener {
            val tabFragment =
                requireActivity().supportFragmentManager.findFragmentByTag(ProfileTabFragment.TAG)!!
            val fragmentManager = tabFragment.childFragmentManager

            fragmentManager.beginTransaction().apply {
                fragmentManager.fragments.forEach(this::detach)
                add(R.id.profile_fragment_container, ChangePasswordFragment.newInstance())
                addToBackStack(null)
                commit()
            }
        }

        binding.logoutButton.setOnClickListener {
            Toast.makeText(context, binding.logoutButton.text, Toast.LENGTH_SHORT).show()
        }
    }
}