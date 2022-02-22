package ru.fefu.activitytracker.screens.new_activity

import androidx.recyclerview.selection.ItemKeyProvider

class ActivityTypeKeyProvider(private val adapter: ActivityTypesAdapter) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {

    override fun getKey(position: Int): Long = adapter.currentList[position].id.toLong()

    override fun getPosition(key: Long): Int =
        adapter.currentList.indexOfFirst { it.id.toLong() == key }
}

