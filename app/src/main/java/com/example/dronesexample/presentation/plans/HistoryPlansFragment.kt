package com.example.dronesexample.presentation.plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dronesexample.R
import com.example.dronesexample.databinding.CurrentPlansFragmentBinding
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.plans.adapter.CurrentPlansAdapter
import com.example.dronesexample.presentation.plans.validator.PlansDateValidator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class HistoryPlansFragment: Fragment(R.layout.current_plans_fragment) {

    private val binding: CurrentPlansFragmentBinding by lazy { initBinding() }
    private val plansViewModel: PlansViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validator = PlansDateValidator(Calendar.getInstance())
        val recyclerViewAdapter = CurrentPlansAdapter(false) {}

        binding.rvCurrentPlans.adapter = recyclerViewAdapter
        binding.rvCurrentPlans.layoutManager = LinearLayoutManager(requireContext())


        plansViewModel.visiblePlans.observe(viewLifecycleOwner) {
            recyclerViewAdapter.submitList(it)
        }

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) showLayout() else hideLayout()
        }

        plansViewModel.loadHistory(validator)
    }

    override fun onDestroyView() {

        plansViewModel.onHistoryNotVisible()

        super.onDestroyView()
    }

    private fun hideLayout() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.tvUserNotAuthorized.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.root.children.forEach { it.visibility = View.VISIBLE }
        binding.tvUserNotAuthorized.visibility = View.GONE
        binding.spinnerPlansFilter.visibility = View.GONE
        binding.etFilterDateAfter.visibility = View.GONE
        binding.etFilterDateFrom.visibility = View.GONE
        binding.btnFilterPlans.visibility  = View.GONE
    }

    private fun initBinding() = CurrentPlansFragmentBinding.bind(requireView())

}