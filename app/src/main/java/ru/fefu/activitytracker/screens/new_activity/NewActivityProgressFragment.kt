package ru.fefu.activitytracker.screens.new_activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.joda.time.Period
import org.joda.time.format.PeriodFormatterBuilder
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.ActivityType
import ru.fefu.activitytracker.databinding.FragmentNewActivityProgressBinding

class NewActivityProgressFragment :
    BindingFragment<FragmentNewActivityProgressBinding>(R.layout.fragment_new_activity_progress) {

    companion object {
        private val durationFormatter = PeriodFormatterBuilder()
            .printZeroAlways().minimumPrintedDigits(2)
            .appendHours().appendSuffix(":").appendMinutes().appendSuffix(":").appendSeconds()
            .toFormatter()

        private const val EXTRA_ACTIVITY_TYPE_ID = "activity_type_ID"

        fun newInstance(activityTypeId: Int) =
            NewActivityProgressFragment().apply {
                val bundle = Bundle()
                bundle.putInt(EXTRA_ACTIVITY_TYPE_ID, activityTypeId)
                arguments = bundle
            }
    }

    private val activityTypeId: Int by lazy {
        requireArguments().getInt(EXTRA_ACTIVITY_TYPE_ID)
    }

    private val activityType: ActivityType by lazy {
        App.INSTANCE.db.activityTypeDao().getById(activityTypeId)
    }

    override fun bind(view: View): FragmentNewActivityProgressBinding =
        FragmentNewActivityProgressBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpButtonHandlers()
    }

    private fun setUpViews() {
        with(binding) {
            activityTypeLabel.text = getString(activityType.nameId)
            distanceLabel.text = requireContext().getString(R.string.kilometers, 14.0)
            durationLabel.text = durationFormatter.print(Period(0, 0, 0, 0, 0, 1, 46, 0))
        }
    }

    private fun setUpButtonHandlers() {
        binding.pauseFab.setOnClickListener {
            Toast.makeText(context, binding.pauseFab.contentDescription, Toast.LENGTH_SHORT).show()
        }
        binding.finishFab.setOnClickListener {
            Toast.makeText(context, binding.finishFab.contentDescription, Toast.LENGTH_SHORT).show()
        }
    }
}