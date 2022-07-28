package com.example.dronesexample.presentation.plans

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dronesexample.R
import com.example.dronesexample.databinding.CurrentPlansFragmentBinding
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.plans.adapter.CurrentPlansAdapter

class FavoritePlansFragment: Fragment(R.layout.current_plans_fragment) {

    private val binding: CurrentPlansFragmentBinding by lazy { initBinding() }
    private val plansViewModel: PlansViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it == "false") hideLayout()
            if (it == "true") showLayout()
        }

        val currentPlansAdapter = CurrentPlansAdapter { flightPlansPR ->
            plansViewModel.onFavoriteStatusChanged(item = flightPlansPR)
        }

        plansViewModel.loadFavorites()

        plansViewModel.favoritePlans.observe(viewLifecycleOwner) { currentPlansAdapter.submitList(it) }

        binding.rvCurrentPlans.adapter = currentPlansAdapter
        binding.rvCurrentPlans.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun hideLayout() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.tvUserNotAuthorized.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.root.children.forEach { it.visibility = View.VISIBLE }
        binding.tvUserNotAuthorized.visibility = View.GONE
    }

    private fun initBinding() = CurrentPlansFragmentBinding.bind(requireView())
}