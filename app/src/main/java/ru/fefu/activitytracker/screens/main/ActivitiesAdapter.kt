package ru.fefu.activitytracker.screens.main

import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.joda.time.DateTime
import ru.fefu.activitytracker.NoUnderlineClickableSpan
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.Activity
import ru.fefu.activitytracker.data.db.ActivityWithType
import ru.fefu.activitytracker.extensions.getDistanceDisplayText
import ru.fefu.activitytracker.extensions.getDurationDisplayText
import ru.fefu.activitytracker.extensions.getEndDateDisplayText
import ru.fefu.activitytracker.screens.main.ActivitiesAdapter.ActivityListItem
import ru.fefu.activitytracker.screens.main.ActivitiesAdapter.ActivityListItem.ActivitiesTitle
import ru.fefu.activitytracker.screens.main.ActivitiesAdapter.ActivityListItem.ActivityCard
import java.text.SimpleDateFormat
import java.util.*

class ActivitiesAdapter(
    private val isCurrentUser: Boolean = false,
    private val onClick: (activity: ActivityWithType) -> Unit,
) : ListAdapter<ActivityListItem, RecyclerView.ViewHolder>(ActivityListItemDiffUtil()) {

    sealed class ActivityListItem {
        data class ActivitiesTitle(val dateTime: DateTime) : ActivityListItem() {
            companion object {
                const val TYPE = 1
            }
        }

        data class ActivityCard(val activity: ActivityWithType) : ActivityListItem() {
            companion object {
                const val TYPE = 2
            }
        }
    }

    class ActivityListItemDiffUtil : DiffUtil.ItemCallback<ActivityListItem>() {
        override fun areItemsTheSame(
            oldItem: ActivityListItem,
            newItem: ActivityListItem
        ): Boolean {
            if (oldItem::class != newItem::class) {
                return false
            }
            return when (oldItem) {
                is ActivityCard ->
                    oldItem.activity.activity.id == (newItem as ActivityCard).activity.activity.id
                is ActivitiesTitle -> oldItem.dateTime == (newItem as ActivitiesTitle).dateTime
            }
        }

        override fun areContentsTheSame(
            oldItem: ActivityListItem,
            newItem: ActivityListItem,
        ): Boolean = oldItem == newItem
    }

    inner class ActivitiesTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.activities_title_label)

        private val formatter = SimpleDateFormat("LLLL yyyy", Locale.getDefault())

        fun bind(item: ActivitiesTitle) {
            val date = formatter.format(item.dateTime.toDate())
            title.text = itemView.context.getString(
                R.string.of_year,
                "${date.substring(0, 1).uppercase()}${date.substring(1)}",
            )
        }
    }

    inner class ActivityCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.title_label)
        private val duration: TextView = itemView.findViewById(R.id.duration_label)
        private val endDate: TextView = itemView.findViewById(R.id.end_date_label)
        private val username: TextView = itemView.findViewById(R.id.username_label)
        private val distance: TextView = itemView.findViewById(R.id.distance_label)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClick((getItem(adapterPosition) as ActivityCard).activity)
                }
            }
        }

        fun bind(item: ActivityCard) {
            val activity = item.activity
            title.text = itemView.context.getString(item.activity.activityType.nameId)
            duration.text = activity.activity.getDurationDisplayText(itemView.context)
            endDate.text = activity.activity.getEndDateDisplayText(itemView.context)
            distance.text = activity.activity.getDistanceDisplayText(itemView.context)
            when {
                isCurrentUser -> username.visibility = View.INVISIBLE
                else -> initializeUsername(activity.activity)
            }
        }

        private fun initializeUsername(activity: Activity) {
            val usernameText = "@${activity.username}"
            val spannable = SpannableString(usernameText).apply {
                setSpan(
                    object : NoUnderlineClickableSpan() {
                        override fun onClick(p0: View) {
                            Toast.makeText(itemView.context, usernameText, Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    0,
                    usernameText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
                )
            }

            username.apply {
                text = spannable
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    fun setActivities(activities: List<ActivityWithType>) {
        val items = mutableListOf<ActivityListItem>()

        activities.sortedByDescending { it.activity.endDateTime }
            .forEachIndexed { index, activity ->
                val shouldAddTitle: Boolean = when (index) {
                    0 -> true
                    else -> {
                        val previousDate = activities[index - 1].activity.endDateTime
                        val currentDate = activity.activity.endDateTime
                        val isSameMonthOfYear = previousDate.monthOfYear == currentDate.monthOfYear
                        val isSameYear = previousDate.year == currentDate.year
                        !isSameYear || !isSameMonthOfYear
                    }
                }
                if (shouldAddTitle) {
                    items.add(ActivitiesTitle(activity.activity.endDateTime))
                }
                items.add(ActivityCard(activity))
            }

        submitList(items)
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ActivitiesTitle -> ActivitiesTitle.TYPE
        is ActivityCard -> ActivityCard.TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ActivityCard.TYPE -> ActivityCardViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_activity, parent, false)
            )
            ActivitiesTitle.TYPE -> ActivitiesTitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.label_activities_title, parent, false)
            )
            else -> throw IllegalArgumentException(
                "View holder for type $viewType isn't provided."
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ActivitiesTitle -> (holder as ActivitiesTitleViewHolder).bind(item)
            is ActivityCard -> (holder as ActivityCardViewHolder).bind(item)
        }
    }
}