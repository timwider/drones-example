package com.example.dronesexample.presentation.map.selection

import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import com.example.dronesexample.presentation.map.MapMode
import com.example.dronesexample.presentation.map.MapScale

interface BaseMapSettingsSelectionManager {

    fun manageMapModeSelection(mapMode: MapMode, regular: Button, satellite: Button, hybrid: Button)

    fun manageMapScaleSelection(mapScale: MapScale, regular: Button, zoomIn: Button, zoomOut: Button)

    fun resetColors(array: Array<Button>)

    fun setDefaultColor(button: Button)

    fun setSelectedColor(button: Button)
}