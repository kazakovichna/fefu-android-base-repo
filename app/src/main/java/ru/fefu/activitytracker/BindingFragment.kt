package ru.fefu.activitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<V : ViewBinding>(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private var _binding: V? = null

    protected val binding: V
        get() = _binding!!

    abstract fun bind(view: View): V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        _binding = bind(view)
        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}