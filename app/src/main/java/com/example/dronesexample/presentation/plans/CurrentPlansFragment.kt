package com.example.dronesexample.presentation.plans

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dronesexample.R
import com.example.dronesexample.databinding.CurrentPlansFragmentBinding
import com.example.dronesexample.presentation.home.HomeViewModel
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.plans.adapter.CurrentPlansAdapter

class CurrentPlansFragment: Fragment(R.layout.current_plans_fragment) {

    private val plansViewModel: PlansViewModel by activityViewModels()
    private val binding: CurrentPlansFragmentBinding by lazy { initBinding() }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) showLayout() else hideLayout()
        }

        val currentPlansAdapter = CurrentPlansAdapter(false) { flightPlansPR ->
            plansViewModel.toggleFavorite(flightPlansPR.isFavorite!!, flightPlansPR.detailsId!!)
            flightPlansPR.isFavorite = !flightPlansPR.isFavorite!!
        }

        plansViewModel.visiblePlans.observe(viewLifecycleOwner) { currentPlansAdapter.submitList(it) }

        plansViewModel.allPlansData.observe(viewLifecycleOwner) { flightPlansData ->
            plansViewModel.loadDetails(flightPlansData, homeViewModel.drones.value!!)
        }

        plansViewModel.loadAllFlightPlans()
        plansViewModel.loadFavorites()

        binding.rvCurrentPlans.adapter = currentPlansAdapter
        binding.rvCurrentPlans.layoutManager = LinearLayoutManager(requireContext())

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

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.rvCurrentPlans.adapter?.notifyDataSetChanged()
    }

    private fun toggleSortLayoutVisibility(param: Int) {
        binding.etFilterDateFrom.visibility = param
        binding.etFilterDateAfter.visibility = param
        binding.btnFilterPlans.visibility = param
    }

    private fun getSpinnerListener(): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                plansViewModel.onFilterItemSelected(position, false)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
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