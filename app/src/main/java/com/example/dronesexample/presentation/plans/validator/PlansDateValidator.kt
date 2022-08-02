package com.example.dronesexample.presentation.plans.validator

import com.example.dronesexample.data.models.FlightPlansRV
import java.util.*

class PlansDateValidator(
    calendar: Calendar,
) {
    private val currentYear = calendar.get(Calendar.YEAR)
    private val currentMonth = calendar.get(Calendar.MONTH) + 1 // отдает 0-11, поэтому + 1
    private val currentDay = calendar.get(Calendar.DAY_OF_MONTH)


    fun sortFlightPlans(plans: List<FlightPlansRV>): List<FlightPlansRV> {
        val validatedPlans = validateFlightPlans(plans)
        return sortByHistory(extractHistorical(validatedPlans))
    }

    // достаем только те, которые уже завершились
    private fun extractHistorical(validatedPlans: List<FlightPlansRV>): List<FlightPlansRV> {
        val historicalPlans = mutableListOf<FlightPlansRV>()

        for (plan in validatedPlans) {
            val year = plan.periodEnd!!.split("-")[0].toInt()
            val month = plan.periodEnd!!.split("-")[1].toInt()
            val day = plan.periodEnd!!.split("-")[2].toInt()

            if (year <= currentYear && month <= currentMonth && day < currentDay) historicalPlans.add(plan)
        }
        return historicalPlans.toList()
    }

    private fun sortByHistory(validatedPlans: List<FlightPlansRV>): List<FlightPlansRV> {
        if (validatedPlans.isNotEmpty()) {
            return validatedPlans.sortedWith(
                compareBy(
                    { it.periodEnd!!.split("-")[0].toInt() },
                    { it.periodEnd!!.split("-")[1].toInt() },
                    { it.periodEnd!!.split("-")[2].toInt() }
                )
            )
        }
        return emptyList()
    }

    // возвращает валидные планы с нужной датой. если валидация не нужна, можно не вызывать
    private fun validateFlightPlans(plans: List<FlightPlansRV>): List<FlightPlansRV> {

        val validatedPlans = mutableListOf<FlightPlansRV>()

        for (plan in plans) {
            val result = checkDateFormat(plan)
            // оборачиваем в let потому что из метода проверки может придти null
            result?.let { validatedPlans.add(it) }
        }
        return validatedPlans
    }

    // формат для примера: год-месяц-день-часы:минуты:секунды
    private fun checkDateFormat(plan: FlightPlansRV): FlightPlansRV? {
        // предполагается что они не null
        // в try блоке пытаемся забрать дату и конвертнуть в int, если получаем ошибку - возвращаем null
        val start = plan.periodStart!!
        val end = plan.periodEnd!!

        try {
            val startValues = start.split("-")
            val endValues = end.split("-")

            for (value in 0 until startValues.lastIndex) {
                startValues[value].toInt()
            }

            for (value in 0 until endValues.lastIndex) {
                endValues[value].toInt()
            }

        } catch (e: Exception) {
            return null
        }
        return plan
    }
}