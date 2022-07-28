package com.example.dronesexample.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.example.dronesexample.R
import com.example.dronesexample.databinding.MapFragmentBinding
import com.example.dronesexample.presentation.main_activity.MainViewModel
import com.example.dronesexample.presentation.map.selection.MapSettingsSelectionManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapFragment: BottomSheetDialogFragment() {

    private val binding: MapFragmentBinding by lazy { initBinding() }
    private val mapVieModel: MapViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectionManager = MapSettingsSelectionManager(resources)

        setupObservers(selectionManager)

        binding.btnMapModeRegular.setOnClickListener { mapVieModel.changeMapMode(MapMode.REGULAR) }
        binding.btnMapModeHybrid.setOnClickListener { mapVieModel.changeMapMode(MapMode.HYBRID) }
        binding.btnMapModeSatellite.setOnClickListener { mapVieModel.changeMapMode(MapMode.SATELLITE) }
        binding.btnMapScaleRegular.setOnClickListener { mapVieModel.changeMapScale(MapScale.REGULAR) }
        binding.btnMapScaleZoomedIn.setOnClickListener { mapVieModel.changeMapScale(MapScale.ZOOMED_IN) }
        binding.btnMapScaleZoomedOut.setOnClickListener { mapVieModel.changeMapScale(MapScale.ZOOMED_OUT) }
    }

    private fun setupObservers(selectionManager: MapSettingsSelectionManager) {
        mapVieModel.mapMode.observe(viewLifecycleOwner) { mapMode ->
            selectionManager.manageMapModeSelection(
                mapMode,
                binding.btnMapModeRegular,
                binding.btnMapModeSatellite,
                binding.btnMapModeHybrid
            )
        }

        mapVieModel.mapScale.observe(viewLifecycleOwner) { mapScale ->
            selectionManager.manageMapScaleSelection(
                mapScale,
                binding.btnMapScaleRegular,
                binding.btnMapScaleZoomedIn,
                binding.btnMapScaleZoomedOut
            )
        }

        mainViewModel.isUserLoggedIn.observe(viewLifecycleOwner) {
            if (it == "false") hideLayout()
            if (it == "true") showLayout()
        }
    }

    private fun hideLayout() {
        binding.root.hideChildren()
        binding.tvUserNotAuthorized.visibility = View.VISIBLE
    }

    private fun showLayout() {
        binding.root.showChildren()
        binding.tvUserNotAuthorized.visibility = View.GONE
    }

    private fun initBinding() = MapFragmentBinding.bind(requireView())
}

fun ViewGroup.hideChildren() {
    this.children.forEach { it.visibility = View.GONE }
}

fun ViewGroup.showChildren() {
    this.children.forEach { it.visibility = View.VISIBLE }
}

