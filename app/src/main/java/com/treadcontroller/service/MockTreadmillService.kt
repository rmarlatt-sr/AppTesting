package com.treadcontroller.service

import com.treadcontroller.data.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class MockTreadmillService : TreadmillService {
    private val _state = MutableStateFlow(TreadmillState())
    override val state: StateFlow<TreadmillState> = _state.asStateFlow()

    private var simulationJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private var targetSpeed = 0.0
    private var targetIncline = 0.0
    private var startTimeMillis = 0L
    private var accumulatedTimeSeconds = 0
    private var accumulatedDistance = 0.0
    private var accumulatedCalories = 0.0

    override suspend fun connect() {
        _state.value = _state.value.copy(connectionState = ConnectionState.CONNECTING)
        delay(500) // Simulate connection delay
        _state.value = _state.value.copy(
            connectionState = ConnectionState.CONNECTED,
            maxSpeedMph = 12.0,
            minSpeedMph = 0.0,
            maxInclinePercent = 15.0,
            minInclinePercent = -3.0
        )
    }

    override suspend fun disconnect() {
        simulationJob?.cancel()
        _state.value = TreadmillState()
    }

    override suspend fun startWorkout() {
        startTimeMillis = System.currentTimeMillis()
        accumulatedTimeSeconds = 0
        accumulatedDistance = 0.0
        accumulatedCalories = 0.0
        _state.value = _state.value.copy(
            workoutState = WorkoutState.RUNNING,
            elapsedTimeSeconds = 0,
            distanceMiles = 0.0,
            caloriesBurned = 0.0
        )
        startSimulation()
    }

    override suspend fun pauseWorkout() {
        accumulatedTimeSeconds = _state.value.elapsedTimeSeconds
        targetSpeed = 0.0
        _state.value = _state.value.copy(
            workoutState = WorkoutState.PAUSED,
            targetSpeedMph = 0.0
        )
        // Keep simulation running briefly to ramp speed down to 0
        delay(2500)
        simulationJob?.cancel()
    }

    override suspend fun resumeWorkout() {
        startTimeMillis = System.currentTimeMillis()
        _state.value = _state.value.copy(workoutState = WorkoutState.RUNNING)
        startSimulation()
    }

    override suspend fun stopWorkout() {
        simulationJob?.cancel()
        targetSpeed = 0.0
        targetIncline = 0.0
        _state.value = _state.value.copy(
            workoutState = WorkoutState.IDLE,
            speedMph = 0.0,
            targetSpeedMph = 0.0,
            inclinePercent = 0.0,
            targetInclinePercent = 0.0
        )
    }

    override suspend fun setSpeed(mph: Double) {
        targetSpeed = mph.coerceIn(_state.value.minSpeedMph, _state.value.maxSpeedMph)
        _state.value = _state.value.copy(targetSpeedMph = targetSpeed)
    }

    override suspend fun setIncline(percent: Double) {
        targetIncline = percent.coerceIn(_state.value.minInclinePercent, _state.value.maxInclinePercent)
        _state.value = _state.value.copy(targetInclinePercent = targetIncline)
    }

    override suspend fun setFan(state: FanState) {
        _state.value = _state.value.copy(fanState = state)
    }

    private fun startSimulation() {
        simulationJob?.cancel()
        simulationJob = scope.launch {
            while (isActive) {
                delay(500) // Update every 500ms
                val current = _state.value
                if (current.workoutState != WorkoutState.RUNNING) continue

                // Simulate gradual speed/incline changes (ramp over ~2 seconds)
                val speedStep = 0.25
                val inclineStep = 0.25
                val newSpeed = when {
                    abs(current.speedMph - targetSpeed) < speedStep -> targetSpeed
                    current.speedMph < targetSpeed -> min(current.speedMph + speedStep, targetSpeed)
                    else -> max(current.speedMph - speedStep, targetSpeed)
                }
                val newIncline = when {
                    abs(current.inclinePercent - targetIncline) < inclineStep -> targetIncline
                    current.inclinePercent < targetIncline -> min(current.inclinePercent + inclineStep, targetIncline)
                    else -> max(current.inclinePercent - inclineStep, targetIncline)
                }

                // Calculate elapsed time
                val elapsedMs = System.currentTimeMillis() - startTimeMillis
                val totalSeconds = accumulatedTimeSeconds + (elapsedMs / 1000).toInt()

                // Accumulate distance (speed in mph * time in hours)
                val hoursFraction = 0.5 / 3600.0 // 500ms in hours
                accumulatedDistance += newSpeed * hoursFraction

                // Rough calorie calculation (METs-based approximation)
                val mets = if (newSpeed > 0) 1.0 + (newSpeed * 0.8) + (newIncline * 0.3) else 1.0
                accumulatedCalories += mets * 80.0 * hoursFraction // assume 80kg

                // Simulate heart rate
                val baseHr = 70
                val speedHr = (newSpeed * 8).toInt()
                val inclineHr = (newIncline * 2).toInt()
                val noise = Random.nextInt(-2, 3)
                val hr = if (newSpeed > 0) (baseHr + speedHr + inclineHr + noise).coerceIn(60, 200) else 0

                _state.value = current.copy(
                    speedMph = newSpeed,
                    inclinePercent = newIncline,
                    elapsedTimeSeconds = totalSeconds,
                    distanceMiles = Math.round(accumulatedDistance * 100) / 100.0,
                    caloriesBurned = Math.round(accumulatedCalories * 10) / 10.0,
                    heartRateBpm = hr
                )
            }
        }
    }
}
