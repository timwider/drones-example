package com.example.dronesexample.presentation.plans

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
            if (it) showLayout() else hideLayout()
         }

        val currentPlansAdapter = CurrentPlansAdapter(true) { flightPlansPR ->
            plansViewModel.toggleFavorite(true, flightPlansPR.detailsId!!)
            flightPlansPR.isFavorite = false
            plansViewModel.removeFavorite(flightPlansPR)
        }

        binding.rvCurrentPlans.adapter = currentPlansAdapter
        binding.rvCurrentPlans.layoutManager = LinearLayoutManager(requireContext())

        plansViewModel.loadFavorites()

        plansViewModel.isSortingByDate.observe(viewLifecycleOwner) {
            val param = if (it) View.VISIBLE else View.GONE
            toggleSortLayoutVisibility(param)
        }

        binding.spinnerPlansFilter.onItemSelectedListener = getSpinnerListener()

        binding.btnFilterPlans.setOnClickListener {
            plansViewModel.validateSortByDate(
                from = binding.etFilterDateFrom.text.toString(),
                to = binding.etFilterDateAfter.text.toString()
            )
        }

        plansViewModel.favoritePlans.observe(viewLifecycleOwner) { plans ->
            val list = plans.filter { it.isFavorite == true }
            currentPlansAdapter.submitList(list)
        }
    }

    override fun onResume() {
        super.onResume()
        plansViewModel.loadFavorites()
    }

    private fun getSpinnerListener(): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                plansViewModel.onFilterItemSelected(position, true)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    private fun toggleSortLayoutVisibility(param: Int) {
        binding.etFilterDateFrom.visibility = param
        binding.etFilterDateAfter.visibility = param
        binding.btnFilterPlans.visibility = param
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