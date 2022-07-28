package com.example.dronesexample.presentation.plans.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dronesexample.presentation.plans.CurrentPlansFragment
import com.example.dronesexample.presentation.plans.FavoritePlansFragment
import com.example.dronesexample.presentation.plans.HistoryPlansFragment

const val VP_ITEMS_COUNT = 3

class PlansTabsAdapter(fm: FragmentManager, lf: Lifecycle): FragmentStateAdapter(fm, lf) {
    override fun getItemCount(): Int = VP_ITEMS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentPlansFragment()
            1 -> FavoritePlansFragment()
            2 -> HistoryPlansFragment()
            else -> CurrentPlansFragment()
        }
    }
}