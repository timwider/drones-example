package com.example.dronesexample.presentation.plans

import androidx.lifecycle.*
import com.example.dronesexample.data.repository.flight_plans.FlightPlansRepository
import com.example.dronesexample.presentation.plans.mapper.FlightPlansPR
import com.example.dronesexample.utils.notifyObservers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlansViewModel: ViewModel() {

    private val _visiblePlans = MutableLiveData<List<FlightPlansPR>>()
    val visiblePlans = _visiblePlans as LiveData<List<FlightPlansPR>>

    private val _allPlans = MutableLiveData<List<FlightPlansPR>>()
    val allPlans = _visiblePlans as LiveData<List<FlightPlansPR>>

    private val _favoritePlans = MutableLiveData<MutableList<FlightPlansPR>>()
    val favoritePlans = _favoritePlans as LiveData<MutableList<FlightPlansPR>>

    private val _isSortingByDate = MutableLiveData(false)
    val isSortingByDate = _isSortingByDate as LiveData<Boolean>

    private val plansRepository = FlightPlansRepository()

    fun onFilterItemSelected(position: Int) {
        val filterItem = FilterItem.values()[position]
        sortPlans(filterItem)
    }

    fun validateSortByDate(from: String, to: String) {
        // todo filter date here
        sortPlansByDate(from, to)
    }

    private fun sortPlansByDate(from: String, to: String) {
        _visiblePlans.value = _allPlans.value?.filter {
            it.period_start == from && it.period_end == to
        }
    }

    private fun sortPlans(item: FilterItem) {

        _isSortingByDate.value = false

         when (item) {
             FilterItem.ALL -> _visiblePlans.value = _allPlans.value

            FilterItem.STATUS_ALLOWED -> {
                _visiblePlans.value = _allPlans.value?.filter { it.permission == "accepted" }
            }
            FilterItem.STATUS_WAITING -> {
                _visiblePlans.value = _allPlans.value?.filter { it.permission == "declined" }
            }

             FilterItem.BY_DATE -> {
                 _isSortingByDate.value = true
                 _visiblePlans.value = _allPlans.value
             }
        }
    }

//    fun loadAllFlightPlans() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _allPlans.postValue(plansRepository.getFlightPlans())
//            _visiblePlans.postValue(plansRepository.getFlightPlans())
//        }
//    }

    fun loadFavorites() {
        _visiblePlans.value?.filter { it.isFavorite == true }.apply {
            _favoritePlans.value = this?.toMutableList()
        }
    }

    // The status itself changes in adapter because we need to notify it immediately by calling
    // notifyItemChanged(int position)
    fun onFavoriteStatusChanged(item: FlightPlansPR) {
        if (item.isFavorite == true) {
            _favoritePlans.value?.remove(item)
        } else _favoritePlans.value?.add(item)

        _favoritePlans.notifyObservers()
        _visiblePlans.notifyObservers()
    }
}

enum class FilterItem {
    ALL,
    STATUS_ALLOWED,
    STATUS_WAITING,
    BY_DATE
}