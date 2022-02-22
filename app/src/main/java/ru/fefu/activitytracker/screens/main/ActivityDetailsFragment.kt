package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import org.joda.time.format.DateTimeFormat
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.NoUnderlineClickableSpan
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.ActivityWithType
import ru.fefu.activitytracker.databinding.FragmentActivityDetailsBinding
import ru.fefu.activitytracker.extensions.getDistanceDisplayText
import ru.fefu.activitytracker.extensions.getDurationDisplayText
import ru.fefu.activitytracker.extensions.getEndDateDisplayText

class ActivityDetailsFragment :
    BindingFragment<FragmentActivityDetailsBinding>(R.layout.fragment_activity_details) {
    companion object {
        private const val EXTRA_ACTIVITY_ID = "activity_id"
        private const val EXTRA_IS_CURRENT_USER = "is_current_user"

        fun newInstance(activityId: Int, isCurrentUser: Boolean = false) =
            ActivityDetailsFragment().apply {
                val bundle = Bundle()
                bundle.putInt(EXTRA_ACTIVITY_ID, activityId)
                bundle.putBoolean(EXTRA_IS_CURRENT_USER, isCurrentUser)
                arguments = bundle
            }
    }

    private val isCurrentUser: Boolean by lazy {
        requireArguments().getBoolean(EXTRA_IS_CURRENT_USER)
    }

    private val activityId: Int by lazy { requireArguments().getInt(EXTRA_ACTIVITY_ID) }


    private val activity: ActivityWithType by lazy {
        App.INSTANCE.db.activityDao().getByIdWithType(activityId)
    }

    override fun bind(view: View): FragmentActivityDetailsBinding =
        FragmentActivityDetailsBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCommonViews()
        when (isCurrentUser) {
            true -> setUpCurrentUserViews()
            false -> setUpAnotherUserViews()
        }
    }

    private fun setUpCommonViews() {
        binding.commentEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.distanceLabel.text = activity.activity.getDistanceDisplayText(requireContext())
        binding.endDateLabel.text = activity.activity.getEndDateDisplayText(requireContext())
        binding.durationLabel.text =
            activity.activity.getDurationDisplayText(requireContext(), useAbbreviations = true)
        binding.commentEditText.setText(activity.activity.comment)

        val formatter = DateTimeFormat.forPattern("HH:mm")
        binding.startTimeLabel.text = activity.activity.startDateTime.toString(formatter)
        binding.endTimeLabel.text = activity.activity.endDateTime.toString(formatter)

        with(binding.toolbar) {
            title = getString(activity.activityType.nameId)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun setUpCurrentUserViews() {
        binding.usernameLabel.visibility = View.GONE
        with(binding.toolbar) {
            inflateMenu(R.menu.my_activity_actions)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_delete_activity, R.id.action_share_activity -> {
                        Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setUpAnotherUserViews() {
        with(binding.commentTextInputLayout) {
            isEnabled = false
            setBoxBackgroundColorResource(R.color.disabled)
        }
        initializeUsername()
    }

    private fun initializeUsername() {
        val usernameText = "@${activity.activity.username}"
        val spannable = SpannableString(usernameText).apply {
            setSpan(
                object : NoUnderlineClickableSpan() {
                    override fun onClick(p0: View) {
                        Toast.makeText(context, usernameText, Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                0,
                usernameText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }

        binding.usernameLabel.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}