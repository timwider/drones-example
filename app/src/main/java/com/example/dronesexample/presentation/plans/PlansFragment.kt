package com.example.dronesexample.presentation.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.dronesexample.R
import com.example.dronesexample.databinding.PlansFragmentBinding
import com.example.dronesexample.presentation.plans.adapter.PlansTabsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class PlansFragment: BottomSheetDialogFragment() {

    private val binding: PlansFragmentBinding by lazy { initBinding() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.plans_fragment, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = PlansTabsAdapter(childFragmentManager, this.lifecycle)
        setupTabMediator()
    }

    private fun initBinding() = PlansFragmentBinding.bind(requireView())

    private fun setupTabMediator() {
        val tabLayout = binding.tabLayout
        val mediator = TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Текущие"
                1 -> tab.text = "Избранное"
                2 -> tab.text = "История"
            }
        }
        mediator.attach()
    }
}