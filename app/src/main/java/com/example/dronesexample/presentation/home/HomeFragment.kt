package com.example.dronesexample.presentation.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dronesexample.R
import com.example.dronesexample.databinding.HomeFragmentBinding
import com.example.dronesexample.models.Drone
import com.example.dronesexample.models.Profile
import com.example.dronesexample.presentation.drones.EditDronesFragment
import com.example.dronesexample.presentation.home.recyclerview_drones.DronesAdapter
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.map.MapFragment
import com.example.dronesexample.presentation.plans.PlansFragment
import com.example.dronesexample.presentation.profile.EditProfileFragment

class HomeFragment: Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by lazy { initBinding() }
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dronesAdapter = DronesAdapter { drone -> showAlertDialog(drone) }

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it == "false") hideLayout()
            if (it == "true") showLayout()
        }

        binding.rvDrones.adapter = dronesAdapter
        binding.rvDrones.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.drones.observe(viewLifecycleOwner) { drones -> dronesAdapter.submitList(drones) }
        homeViewModel.profileData.observe(viewLifecycleOwner) { profile -> setDataFromProfile(profile) }

        homeViewModel.getProfileData()
        homeViewModel.getAllDrones()

        setupBottomNav()

        binding.ivShowMap.setOnClickListener { MapFragment().show(parentFragmentManager, null) }
    }

    private fun showAlertDialog(drone: Drone) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить БВС")
            .setMessage("Вы уверены, что хотите удалить БВС?")
            .setPositiveButton("Да") { _, _ -> homeViewModel.deleteDrone(drone) }
            .setNegativeButton("Отменить") { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
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

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.edit_plans -> PlansFragment().show(parentFragmentManager, null)
                R.id.drones -> EditDronesFragment().show(childFragmentManager, null)
                R.id.edit_profile ->
                    parentFragmentManager.commit {
                        replace<EditProfileFragment>(R.id.main_fragment_container)
                    }
            }
            return@setOnItemSelectedListener true
        }

    }
    private fun initBinding() = HomeFragmentBinding.bind(requireView())
}