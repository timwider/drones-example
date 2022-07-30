package com.example.dronesexample.presentation.drones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.*
import com.example.dronesexample.R
import com.example.dronesexample.databinding.EditDronesFragmentBinding
import com.example.dronesexample.presentation.home.HomeViewModel
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditDronesFragment: BottomSheetDialogFragment() {

    private val binding: EditDronesFragmentBinding by lazy { initBinding() }
    private val editDronesViewModel: DronesViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_drones_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it) showLayout() else hideLayout()
        }

        editDronesViewModel.dataSavedSuccessfully.observe(viewLifecycleOwner) {
            it?.let { homeViewModel.addDrone(it); dialog?.dismiss() }
        }

        binding.btnSubmit.setOnClickListener { onSubmitClicked() }
        binding.btnCancel.setOnClickListener { dialog?.dismiss() }
    }

    private fun hideLayout() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.tvUserNotAuthorized.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.root.children.forEach { it.visibility = View.VISIBLE }
        binding.tvUserNotAuthorized.visibility = View.GONE
    }

    private fun onSubmitClicked() {
        editDronesViewModel.validateData(
            name = binding.etDroneModel.text.toString(),
            serial = binding.etDroneSerialNumber.text.toString(),
            weight = binding.etDroneWeight.text.toString()
        ) {
            editDronesViewModel.setDrone(it)
        }
    }

    private fun initBinding() = EditDronesFragmentBinding.bind(requireView())
}