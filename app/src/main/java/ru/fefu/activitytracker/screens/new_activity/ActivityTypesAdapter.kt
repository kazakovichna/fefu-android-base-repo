package ru.fefu.activitytracker.screens.new_activity

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.ActivityType

class ActivityTypesAdapter :
    ListAdapter<ActivityType, ActivityTypesAdapter.ActivityTypeUiViewHolder>(
        ActivityTypeUiDiffCallback()
    ) {

    class ActivityTypeUiDiffCallback : DiffUtil.ItemCallback<ActivityType>() {
        override fun areItemsTheSame(oldItem: ActivityType, newItem: ActivityType): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ActivityType, newItem: ActivityType): Boolean =
            oldItem == newItem
    }

    inner class ActivityTypeUiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = itemView.findViewById(R.id.label_activity_type_title)
        private val image: ImageView = itemView.findViewById(R.id.ic_activity_type)

        fun bind(item: ActivityType, isSelected: Boolean) {
            title.text = itemView.context.getString(item.nameId)
            image.setImageResource(item.imageId)

            with(itemView as MaterialCardView) {
                if (isSelected) {
                    strokeWidth =
                        resources.getDimensionPixelSize(R.dimen.selected_activity_type_stroke_width)
                    strokeColor =
                        ResourcesCompat.getColor(resources, R.color.vivid_violet, null)
                } else {
                    strokeWidth =
                        resources.getDimensionPixelSize(R.dimen.activity_type_stroke_width)
                    strokeColor =
                        ResourcesCompat.getColor(resources, R.color.border_gray, null)
                }

            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition

                override fun getSelectionKey(): Long = getItem(adapterPosition).id.toLong()

                override fun inSelectionHotspot(e: MotionEvent): Boolean = true
            }
    }

    lateinit var tracker: SelectionTracker<Long>

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ActivityTypeUiViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_activity_type, viewGroup, false)
        return ActivityTypeUiViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ActivityTypeUiViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item, tracker.isSelected(item.id.toLong()))
    }

    override fun getItemId(position: Int): Long = position.toLong()
}