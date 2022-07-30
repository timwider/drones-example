package com.example.dronesexample.presentation.plans

import android.util.Log
import androidx.lifecycle.*
import com.example.dronesexample.data.models.Details
import com.example.dronesexample.data.models.Drone
import com.example.dronesexample.data.models.FlightPlans
import com.example.dronesexample.data.models.FlightPlansRV
import com.example.dronesexample.data.repository.details.DetailsRepository
import com.example.dronesexample.data.repository.flight_plans.FlightPlansRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlansViewModel: ViewModel() {

    private var _visiblePlans =  MutableLiveData<List<FlightPlansRV>>()
    val visiblePlans = _visiblePlans as LiveData<List<FlightPlansRV>>

    private val _allPlansData = MutableLiveData<List<FlightPlans>>()
    val allPlansData = _allPlansData as LiveData<List<FlightPlans>>

    private val _allPlans = MutableLiveData<List<FlightPlansRV>>()
    val allPlans = _allPlans as LiveData<List<FlightPlansRV>>

    private val _favoritePlans = MutableLiveData<List<FlightPlansRV>>()
    val favoritePlans = _favoritePlans as LiveData<List<FlightPlansRV>>

    private val _isSortingByDate = MutableLiveData(false)
    val isSortingByDate = _isSortingByDate as LiveData<Boolean>

    private val plansRepository = FlightPlansRepository()
    private val detailsRepository = DetailsRepository()


    fun refreshVisiblePlans() { _visiblePlans.value = visiblePlans.value }

    fun toggleFavorite(isFavoriteNow: Boolean, detailsId: String) {
        val newValue = !isFavoriteNow
        viewModelScope.launch(Dispatchers.IO) {
            plansRepository.toggleFavorite(newValue, detailsId) {}
        }
    }

    fun removeFavorite(element: FlightPlansRV) {
        val list = favoritePlans.value
        list?.toMutableList()?.remove(element)
        _favoritePlans.value = list?.toList()
    }

    fun onFilterItemSelected(position: Int, areFavorites: Boolean) {
        val filterItem = FilterItem.values()[position]
        val items = if (areFavorites) _favoritePlans else _visiblePlans
        sortPlans(filterItem, items, areFavorites)
    }

    fun loadDetails(plans: List<FlightPlans>, drones: List<Drone>) {
        viewModelScope.launch {
            detailsRepository.getAllDetails { details ->
                _allPlans.postValue(
                    mapFromData(plans, drones, details)
                )
                _visiblePlans.postValue(
                    mapFromData(plans, drones, details)
                )
            }
        }
    }

    fun validateSortByDate(from: String, to: String) {
        // todo filter date here
        sortPlansByDate(from, to)
    }

    private fun sortPlansByDate(from: String, to: String) {
        _visiblePlans.value = _allPlans.value?.filter {
            it.periodStart == from && it.periodEnd == to
        }
    }

    private fun sortPlans(item: FilterItem, items: MutableLiveData<List<FlightPlansRV>>, areFavorites: Boolean) {

        _isSortingByDate.value = false

        val filteredList: List<FlightPlansRV>? = when (item) {
            FilterItem.ALL -> _allPlans.value

            FilterItem.STATUS_ALLOWED -> _allPlans.value?.filter { it.permission == "accepted" }

            FilterItem.STATUS_WAITING -> _allPlans.value?.filter { it.permission == "declined" }

            FilterItem.BY_DATE -> { _isSortingByDate.value = true; _allPlans.value }
        }

        items.value = if (areFavorites) filteredList?.filter { it.isFavorite == true } else filteredList
    }

    fun loadAllFlightPlans() {
        viewModelScope.launch(Dispatchers.IO) {
            plansRepository.getFlightPlans {
                _allPlansData.postValue(it)
            }
        }
    }

    fun loadFavorites() {
        _visiblePlans.value?.filter { it.isFavorite == true }.apply {
            _favoritePlans.value = this?.toMutableList()
        }
    }

    private fun mapFromData(
        plans: List<FlightPlans>,
        drones: List<Drone>,
        plansDetails: List<Details>,
    ): List<FlightPlansRV> {

        val flightPlansRv = mutableListOf<FlightPlansRV>()

        for (plan in plans) {

            var isFavorite = false
            var droneName = ""
            drones.forEach {
                if (it.uuid == plan.droneUUID) droneName = it.model!!
            }

            for (detail in plansDetails) {
                if (detail.uuid == plan.details_id) isFavorite = detail.favorites!!
            }

            val flightPlanRV = FlightPlansRV(
                uuid = plan.uuid.toString(),
                periodStart = plan.period_start,
                periodEnd = plan.period_end,
                isFavorite = isFavorite,
                droneName = droneName,
                permission = plan.permission,
                detailsId = plan.details_id
            )
            flightPlansRv.add(flightPlanRV)
        }
        return flightPlansRv
    }
}
enum class FilterItem {
    ALL,
    STATUS_ALLOWED,
    STATUS_WAITING,
    BY_DATE
}