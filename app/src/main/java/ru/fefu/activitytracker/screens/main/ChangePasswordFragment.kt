package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment :
    BindingFragment<FragmentChangePasswordBinding>(R.layout.fragment_change_password) {

    companion object {
        fun newInstance() = ChangePasswordFragment()
    }

    override fun bind(view: View): FragmentChangePasswordBinding =
        FragmentChangePasswordBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setUpButtonHandlers()
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setUpButtonHandlers() {
        binding.confirmButton.setOnClickListener {
            Toast.makeText(context, binding.confirmButton.text, Toast.LENGTH_SHORT).show()
        }
    }
}