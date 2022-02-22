package ru.fefu.activitytracker.screens.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ru.fefu.activitytracker.App
import ru.fefu.activitytracker.BindingFragment
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.ActivityWithType
import ru.fefu.activitytracker.databinding.FragmentUserActivityBinding

class UserActivityFragment :
    BindingFragment<FragmentUserActivityBinding>(R.layout.fragment_user_activity) {

    companion object {
        private const val EXTRA_IS_CURRENT_USER = "is_current_user"

        fun newInstance(isCurrentUser: Boolean = false) =
            UserActivityFragment().apply {
                val bundle = Bundle()
                bundle.putBoolean(EXTRA_IS_CURRENT_USER, isCurrentUser)
                arguments = bundle
            }
    }

    private val isCurrentUser: Boolean by lazy {
        requireArguments().getBoolean(EXTRA_IS_CURRENT_USER)
    }

    private lateinit var adapter: ActivitiesAdapter

    override fun bind(view: View): FragmentUserActivityBinding =
        FragmentUserActivityBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActivitiesAdapter(
            isCurrentUser = isCurrentUser,
            onClick = ::openActivityDetails,
        )

        binding.activitiesRecyclerView.adapter = adapter
        binding.activitiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        with(App.INSTANCE.db.activityDao()) {
            if (isCurrentUser) {
                getByUserWithTypes("dev").observe(
                    viewLifecycleOwner,
                    ::handleActivitiesChange,
                )
            } else {
                getAllExceptUserWithTypes("dev").observe(
                    viewLifecycleOwner,
                    ::handleActivitiesChange,
                )
            }
        }
    }

    private fun handleActivitiesChange(activities: List<ActivityWithType>) {
        adapter.setActivities(activities)
        setEmptyListViewVisibility(activities.isEmpty())
    }

    private fun setEmptyListViewVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.startTrainingLabel.visibility = View.VISIBLE
            binding.startTutorialLabel.visibility = View.VISIBLE
        } else {
            binding.startTrainingLabel.visibility = View.GONE
            binding.startTutorialLabel.visibility = View.GONE
        }
    }

    private fun openActivityDetails(activity: ActivityWithType) {
        val tabFragment =
            requireActivity().supportFragmentManager.findFragmentByTag(ActivityTabFragment.TAG)!!
        val fragmentManager = tabFragment.childFragmentManager

        fragmentManager.beginTransaction().apply {
            fragmentManager.fragments.forEach(this::detach)
            add(
                R.id.activity_fragment_container,
                ActivityDetailsFragment.newInstance(
                    activity.activity.id,
                    isCurrentUser = isCurrentUser,
                ),
            )
            addToBackStack(null)
            commit()
        }
    }
}