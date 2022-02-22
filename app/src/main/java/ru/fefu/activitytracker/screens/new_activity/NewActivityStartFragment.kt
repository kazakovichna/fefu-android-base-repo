package ru.fefu.activitytracker.screens.new_activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.SelectionTracker.SelectionPredicate
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.Activity
import ru.fefu.activitytracker.databinding.FragmentNewActivityStartBinding

class NewActivityStartFragment :
    BindingFragment<FragmentNewActivityStartBinding>(R.layout.fragment_new_activity_start) {

    companion object {
        const val ACTIVITY_TYPE_SELECTION_ID = "activity_type"

        fun newInstance() = NewActivityStartFragment()
    }

    private lateinit var tracker: SelectionTracker<Long>

    private val selectedActivityTypeId: Int
        get() = tracker.selection.first().toInt()

    override fun bind(view: View): FragmentNewActivityStartBinding =
        FragmentNewActivityStartBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActivityTypes()
        setUpButtonHandlers()
    }

    private fun setUpActivityTypes() {
        val adapter = ActivityTypesAdapter()

        binding.activityTypesRecyclerView.adapter = adapter
        binding.activityTypesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        tracker = SelectionTracker.Builder(
            ACTIVITY_TYPE_SELECTION_ID,
            binding.activityTypesRecyclerView,
            ActivityTypeKeyProvider(adapter),
            ActivityTypeDetailsLookup(binding.activityTypesRecyclerView),
            StorageStrategy.createLongStorage(),
        ).withSelectionPredicate(
            object : SelectionPredicate<Long>() {
                override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean = nextState

                override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean =
                    nextState

                override fun canSelectMultiple(): Boolean = false
            }
        ).build()

        adapter.tracker = tracker

        App.INSTANCE.db.activityTypeDao().getAll().observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (tracker.selection.isEmpty) {
                tracker.select(it.first().id.toLong())
            }
        }

    }

    private fun setUpButtonHandlers() {
        binding.startButton.setOnClickListener {
            createActivity()
            navigateToActivityProgress()
        }
    }

    private fun createActivity() {
        App.INSTANCE.db.activityDao().insertAll(
            Activity(
                id = 0,
                typeId = selectedActivityTypeId,
                startDateTime = DateTime.now(),
                endDateTime = DateTime.now(),
                username = "dev",
                points = listOf(),
            )
        )
    }

    private fun navigateToActivityProgress() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.new_activity_menu_fragment_container,
                NewActivityProgressFragment.newInstance(selectedActivityTypeId),
            )
            commit()
        }
    }
}