package com.example.dronesexample.presentation.map.selection

import android.content.res.Resources
import android.widget.Button
import com.example.dronesexample.R
import com.example.dronesexample.presentation.map.MAP_SCALE_ZOOMED_IN
import com.example.dronesexample.presentation.map.MapMode
import com.example.dronesexample.presentation.map.MapScale

class MapSettingsSelectionManager(private val resources: Resources): BaseMapSettingsSelectionManager {

    override fun manageMapModeSelection(
        mapMode: MapMode,
        regular: Button,
        satellite: Button,
        hybrid: Button
    ) {
        val buttons = arrayOf(regular, satellite, hybrid)

        resetColors(buttons)

        when (mapMode) {
            MapMode.NOT_SET -> {}
            MapMode.SATELLITE -> setSelectedColor(satellite)
            MapMode.HYBRID -> setSelectedColor(hybrid)
            MapMode.REGULAR -> setSelectedColor(regular)
        }
    }

    override fun manageMapScaleSelection(
        mapScale: MapScale,
        regular: Button,
        zoomIn: Button,
        zoomOut: Button
    ) {
        val buttons = arrayOf(regular, zoomIn, zoomOut)
        resetColors(buttons)

        when (mapScale) {
            MapScale.REGULAR -> setSelectedColor(regular)
            MapScale.ZOOMED_IN -> setSelectedColor(zoomIn)
            MapScale.ZOOMED_OUT -> setSelectedColor(zoomOut)
        }
    }

    override fun resetColors(array: Array<Button>) {
        array.forEach { setDefaultColor(it) }
    }

    override fun setDefaultColor(button: Button) {
        button.setBackgroundColor(resources.getColor(R.color.grey, null))
    }

    override fun setSelectedColor(button: Button) {
        button.setBackgroundColor(resources.getColor(R.color.teal_200, null))
    }
}