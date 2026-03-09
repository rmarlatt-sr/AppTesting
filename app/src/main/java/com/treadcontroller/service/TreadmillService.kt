package com.treadcontroller.service

import com.treadcontroller.data.model.FanState
import com.treadcontroller.data.model.TreadmillState
import kotlinx.coroutines.flow.StateFlow

interface TreadmillService {
    val state: StateFlow<TreadmillState>

    suspend fun connect()
    suspend fun disconnect()

    suspend fun startWorkout()
    suspend fun pauseWorkout()
    suspend fun resumeWorkout()
    suspend fun stopWorkout()

    suspend fun setSpeed(mph: Double)
    suspend fun setIncline(percent: Double)
    suspend fun setFan(state: FanState)
}
