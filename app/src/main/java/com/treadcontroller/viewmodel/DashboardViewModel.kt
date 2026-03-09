package com.treadcontroller.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treadcontroller.data.model.FanState
import com.treadcontroller.data.model.TreadmillState
import com.treadcontroller.service.TreadmillService
import com.treadcontroller.service.WorkoutForegroundService
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val treadmillService: TreadmillService,
    private val appContext: Context
) : ViewModel() {
    val treadmillState: StateFlow<TreadmillState> = treadmillService.state

    init {
        viewModelScope.launch { treadmillService.connect() }
    }

    fun startWorkout() {
        Log.d("TC_DEBUG", "startWorkout CLICKED, current state=${treadmillState.value.workoutState}")
        viewModelScope.launch {
            treadmillService.startWorkout()
            Log.d("TC_DEBUG", "startWorkout DONE, new state=${treadmillState.value.workoutState}")
            WorkoutForegroundService.start(appContext)
        }
    }

    fun pauseWorkout() {
        Log.d("TC_DEBUG", "pauseWorkout CLICKED, current state=${treadmillState.value.workoutState}")
        viewModelScope.launch {
            treadmillService.pauseWorkout()
            Log.d("TC_DEBUG", "pauseWorkout DONE, new state=${treadmillState.value.workoutState}")
        }
    }

    fun resumeWorkout() {
        Log.d("TC_DEBUG", "resumeWorkout CLICKED, current state=${treadmillState.value.workoutState}")
        viewModelScope.launch { treadmillService.resumeWorkout() }
    }

    fun stopWorkout() {
        Log.d("TC_DEBUG", "stopWorkout CLICKED, current state=${treadmillState.value.workoutState}")
        viewModelScope.launch {
            treadmillService.stopWorkout()
            WorkoutForegroundService.stop(appContext)
        }
    }

    fun setSpeed(mph: Double) = viewModelScope.launch { treadmillService.setSpeed(mph) }
    fun setIncline(percent: Double) = viewModelScope.launch { treadmillService.setIncline(percent) }
    fun setFan(state: FanState) = viewModelScope.launch { treadmillService.setFan(state) }
}
