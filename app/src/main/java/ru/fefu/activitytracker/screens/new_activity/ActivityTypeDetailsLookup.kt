package ru.fefu.activitytracker.screens.new_activity

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.screens.new_activity.ActivityTypesAdapter.ActivityTypeUiViewHolder

class ActivityTypeDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ActivityTypeUiViewHolder)
                .getItemDetails()
        }
        return null
    }
}
