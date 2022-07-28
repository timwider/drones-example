package com.example.dronesexample.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val MAP_SCALE_REGULAR = 100
const val MAP_SCALE_ZOOMED_IN = 10
const val MAP_SCALE_ZOOMED_OUT = 500


class MapViewModel: ViewModel() {

    private val _mapMode = MutableLiveData(MapMode.NOT_SET)
    val mapMode = _mapMode as LiveData<MapMode>

    private val _mapScale = MutableLiveData(MapScale.REGULAR)
    val mapScale = _mapScale as LiveData<MapScale>

    fun changeMapMode(mapMode: MapMode) { if (mapMode != _mapMode.value) { _mapMode.value = mapMode } }

    fun changeMapScale(mapScale: MapScale) { if (mapScale != _mapScale.value) { _mapScale.value = mapScale } }

    private fun checkMapScale(mapScale: Int) {
        if (mapScale != MAP_SCALE_REGULAR ||
            mapScale != MAP_SCALE_ZOOMED_IN ||
            mapScale != MAP_SCALE_ZOOMED_OUT)
        {
            throw MapScaleException()
        }
    }

}

class MapScaleException: Exception() {
    override val message: String = "Invalid map scale. It should be either MAP_SCALE_REGULAR(100), MAP_SCALE_ZOOMED_IN(10) or MAP_SCALE_ZOOMED_OUT(500)."
}

enum class MapMode {
    NOT_SET,
    REGULAR,
    SATELLITE,
    HYBRID
}

enum class MapScale {
    REGULAR,
    ZOOMED_IN,
    ZOOMED_OUT
}