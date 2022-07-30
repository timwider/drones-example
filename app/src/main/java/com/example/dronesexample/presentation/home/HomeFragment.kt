package com.example.dronesexample.presentation.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dronesexample.R
import com.example.dronesexample.data.FirebaseDatabaseReference
import com.example.dronesexample.databinding.HomeFragmentBinding
import com.example.dronesexample.data.models.Profile
import com.example.dronesexample.data.repository.flight_plans.FlightPlansRepository
import com.example.dronesexample.presentation.drones.EditDronesFragment
import com.example.dronesexample.presentation.edit_plans.EditPlansFragment
import com.example.dronesexample.presentation.home.recyclerview_drones.DronesAdapter
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.map.MapFragment
import com.example.dronesexample.presentation.navigation.Destination
import com.example.dronesexample.presentation.navigation.NavigationManager
import com.example.dronesexample.presentation.plans.PlansFragment
import com.example.dronesexample.presentation.profile.EditProfileFragment
import com.example.dronesexample.presentation.request.RequestFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class HomeFragment: Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by lazy { initBinding() }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dronesAdapter = DronesAdapter { drone -> homeViewModel.deleteDrone(drone) }

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) showLayout() else hideLayout()
        }

        binding.rvDrones.adapter = dronesAdapter
        binding.rvDrones.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.drones.observe(viewLifecycleOwner) { drones ->
            dronesAdapter.submitList(drones)
        }
        homeViewModel.profileData.observe(viewLifecycleOwner) { profile -> setDataFromProfile(profile) }

        homeViewModel.getProfileData()
        homeViewModel.getAllDrones()

        setupBottomNav()

        binding.ivShowMap.setOnClickListener { MapFragment().show(parentFragmentManager, null) }

        binding.ivRequest.setOnClickListener {
            NavigationManager(parentFragmentManager).navigate(Destination.REQUEST)
        }
    }

    private fun hideLayout() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.tvUserNotAuthorized.visibility = View.VISIBLE
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.root.children.forEach { it.visibility = View.VISIBLE }
        binding.tvUserNotAuthorized.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setDataFromProfile(profile: Profile) {
        binding.tvName.text = "${profile.first_name} ${profile.last_name}"
        binding.tvEmail.text = profile.email
        binding.tvPhoneNumber.text = profile.phone
    }

    private fun setupBottomNav() {

        val navigationManager = NavigationManager(parentFragmentManager)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.plans -> checkIfNavigateToPlans(navigationManager, false)

                R.id.drones -> navigationManager.navigate(Destination.DRONES)

                R.id.edit_profile -> navigationManager.navigate(Destination.PROFILE)

                R.id.add_plan-> checkIfNavigateToPlans(navigationManager, true)
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun checkIfNavigateToPlans(navigationManager: NavigationManager, toEditPlans: Boolean) {
        if (!homeViewModel.drones.value.isNullOrEmpty()) {

            if (toEditPlans) {
                navigationManager.navigate(Destination.EDIT_PLANS)
            } else navigationManager.navigate(Destination.PLANS)

        } else Snackbar.make(requireView(), "Сначала добавьте БВС", Snackbar.LENGTH_SHORT).show()
    }

    private fun initBinding() = HomeFragmentBinding.bind(requireView())
}