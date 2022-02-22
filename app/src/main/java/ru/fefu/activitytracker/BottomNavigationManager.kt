package ru.fefu.activitytracker

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationManager(
    private val fragmentManager: FragmentManager,
    private val navigationView: BottomNavigationView,
    private val fragmentContainerId: Int,
    private val mainNavigationItemId: Int,
    navigationItems: Iterable<NavigationItem>,
    savedInstanceState: Bundle?
) {
    companion object {
        private const val CURRENT_ITEM_ID_KEY = "current_item_id"

        private const val IDS_HISTORY_KEY = "history"
    }

    data class NavigationItem(
        val id: Int,
        val tag: String,
        val fragmentCreator: () -> Fragment,
    )

    private val items = navigationItems.map { it.id to it }.toMap()

    private val idsHistory = ArrayDeque<Int>(initialCapacity = items.size)

    private lateinit var currentItem: NavigationItem

    val currentFragment: Fragment?
        get() = fragmentManager.findFragmentByTag(currentItem.tag)

    init {
        when (savedInstanceState) {
            null -> createState()
            else -> restoreState(savedInstanceState)
        }
        setNavigationViewListeners()
    }

    fun dispose() {
        clearNavigationViewListeners()
    }

    fun handleBackPressed(): Boolean {
        if (idsHistory.isEmpty() && currentItem.id == mainNavigationItemId) {
            return false
        }
        openItem(if (idsHistory.isEmpty()) mainNavigationItemId else idsHistory.removeLast())
        updateNavigationViewSelectedItem()
        return true
    }

    fun saveState(outState: Bundle) {
        outState.apply {
            putInt(CURRENT_ITEM_ID_KEY, currentItem.id)
            putIntegerArrayList(IDS_HISTORY_KEY, idsHistory.toCollection(ArrayList<Int>()))
        }
    }

    private fun createState() {
        currentItem = getItem(mainNavigationItemId)
        fragmentManager.beginTransaction().apply {
            add(fragmentContainerId, currentItem.fragmentCreator(), currentItem.tag)
            commit()
        }
    }

    private fun restoreState(savedInstanceState: Bundle) {
        savedInstanceState.apply {
            currentItem = getItem(getInt(CURRENT_ITEM_ID_KEY))
            getIntegerArrayList(IDS_HISTORY_KEY)!!.toCollection(idsHistory)
        }
    }

    private fun getItem(itemId: Int): NavigationItem {
        val item = items[itemId]
        requireNotNull(item) {
            "Navigation items don't contain the one with id $itemId."
        }
        return item
    }

    private fun setNavigationViewListeners() {
        navigationView.setOnItemSelectedListener(::onNavigationItemSelected)
        navigationView.setOnItemReselectedListener(::onNavigationItemReselected)
    }

    private fun clearNavigationViewListeners() {
        navigationView.setOnItemSelectedListener(null)
        navigationView.setOnItemReselectedListener(null)
    }

    private fun updateNavigationViewSelectedItem() {
        clearNavigationViewListeners()
        navigationView.selectedItemId = currentItem.id
        setNavigationViewListeners()
    }

    private fun openItem(itemId: Int) {
        idsHistory.remove(itemId)
        fragmentManager.beginTransaction().apply {
            hide(currentFragment!!)
            currentItem = getItem(itemId)
            when (currentFragment) {
                null -> add(fragmentContainerId, currentItem.fragmentCreator(), currentItem.tag)
                else -> show(currentFragment!!)
            }
            commit()
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        idsHistory.add(currentItem.id)
        openItem(menuItem.itemId)
        return true
    }

    private fun onNavigationItemReselected(
        @Suppress("UNUSED_PARAMETER") menuItem: MenuItem,
    ) = Unit
}