package com.example.dronesexample.presentation.edit_plans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.dronesexample.R
import com.example.dronesexample.databinding.EditPlansFragmentBinding
import com.example.dronesexample.presentation.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class EditPlansFragment: BottomSheetDialogFragment() {

    private val binding: EditPlansFragmentBinding by lazy { initBinding() }
    private val editPlansViewModel: EditPlansViewModel by viewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_plans_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dronesModels = homeViewModel.extractDronesNames()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dronesModels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        editPlansViewModel.validationResult.observe(viewLifecycleOwner) {
            if (!it) {
                Snackbar.make(requireView(), "Некорректные данные.", Snackbar.LENGTH_SHORT).show()
            } else dialog?.dismiss()
        }

        binding.selectDrone.adapter = adapter
        binding.selectDrone.onItemSelectedListener = spinnerListener()

        binding.btnSubmit.setOnClickListener {
            editPlansViewModel.validateData(
            name = binding.etPlanName.text.toString(),
            from = binding.etDateFrom.text.toString(),
            to = binding.etDateTo.text.toString(),
            selectedDroneUUID = editPlansViewModel.selectedDroneUUID.value!!)
        }

        binding.btnCancel.setOnClickListener { dialog?.dismiss() }
    }

    private fun spinnerListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedDroneUUID = homeViewModel.drones.value?.get(position)?.uuid!!
            editPlansViewModel.onDroneSelected(selectedDroneUUID)
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun initBinding() = EditPlansFragmentBinding.bind(requireView())
}