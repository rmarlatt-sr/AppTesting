package com.treadcontroller.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treadcontroller.data.db.WorkoutDao
import com.treadcontroller.data.model.*
import com.treadcontroller.service.TreadmillService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ActiveWorkoutViewModel(
    private val treadmillService: TreadmillService,
    private val workoutDao: WorkoutDao
) : ViewModel() {

    val treadmillState: StateFlow<TreadmillState> = treadmillService.state

    private val _activeTemplate = MutableStateFlow<WorkoutTemplate?>(null)
    val activeTemplate: StateFlow<WorkoutTemplate?> = _activeTemplate

    private val _currentIntervalIndex = MutableStateFlow(0)
    val currentIntervalIndex: StateFlow<Int> = _currentIntervalIndex

    private val _intervalTimeRemaining = MutableStateFlow(0)
    val intervalTimeRemaining: StateFlow<Int> = _intervalTimeRemaining

    // Manual override tracking
    private val _manualSpeedOverride = MutableStateFlow<Double?>(null)
    val manualSpeedOverride: StateFlow<Double?> = _manualSpeedOverride

    private val _manualInclineOverride = MutableStateFlow<Double?>(null)
    val manualInclineOverride: StateFlow<Double?> = _manualInclineOverride

    // If true, manual adjustments persist across intervals
    // If false (default), manual adjustments reset at each new interval
    private val _lockManualOverride = MutableStateFlow(false)
    val lockManualOverride: StateFlow<Boolean> = _lockManualOverride

    private var intervalJob: Job? = null
    private var sessionId: Long? = null
    private val speedSamples = mutableListOf<Double>()
    private val inclineSamples = mutableListOf<Double>()

    fun loadTemplate(template: WorkoutTemplate) {
        _activeTemplate.value = template
        _currentIntervalIndex.value = 0
        _intervalTimeRemaining.value = template.intervals.firstOrNull()?.durationSeconds ?: 0
        _manualSpeedOverride.value = null
        _manualInclineOverride.value = null
    }

    fun startWorkout() {
        viewModelScope.launch {
            treadmillService.startWorkout()

            val template = _activeTemplate.value ?: return@launch
            val session = WorkoutSession(
                templateId = template.id.takeIf { it > 0 },
                templateName = template.name
            )
            sessionId = workoutDao.insertSession(session)

            _currentIntervalIndex.value = 0
            _manualSpeedOverride.value = null
            _manualInclineOverride.value = null
            applyInterval(0)
            startIntervalTimer()
        }
    }

    fun pauseWorkout() = viewModelScope.launch {
        intervalJob?.cancel()
        treadmillService.pauseWorkout()
    }

    fun resumeWorkout() = viewModelScope.launch {
        treadmillService.resumeWorkout()
        startIntervalTimer()
    }

    fun stopWorkout() {
        viewModelScope.launch {
            intervalJob?.cancel()
            treadmillService.stopWorkout()
            _manualSpeedOverride.value = null
            _manualInclineOverride.value = null
            saveSession()
        }
    }

    /**
     * Manual speed adjustment during a programmed workout.
     * This overrides the current interval's target until the next interval
     * (unless lockManualOverride is enabled).
     */
    fun manualSetSpeed(mph: Double) {
        _manualSpeedOverride.value = mph
        viewModelScope.launch { treadmillService.setSpeed(mph) }
    }

    /**
     * Manual incline adjustment during a programmed workout.
     */
    fun manualSetIncline(percent: Double) {
        _manualInclineOverride.value = percent
        viewModelScope.launch { treadmillService.setIncline(percent) }
    }

    /** Toggle whether manual overrides persist across interval transitions. */
    fun toggleLockOverride() {
        _lockManualOverride.value = !_lockManualOverride.value
    }

    /** Clear manual overrides and snap back to the current interval's targets. */
    fun clearOverrides() {
        _manualSpeedOverride.value = null
        _manualInclineOverride.value = null
        applyInterval(_currentIntervalIndex.value)
    }

    private fun applyInterval(index: Int) {
        val template = _activeTemplate.value ?: return
        val interval = template.intervals.getOrNull(index) ?: return
        _intervalTimeRemaining.value = interval.durationSeconds

        // If lock is off, clear manual overrides at interval transition
        if (!_lockManualOverride.value) {
            _manualSpeedOverride.value = null
            _manualInclineOverride.value = null
        }

        // Apply: manual override wins if set, otherwise use interval target
        val speed = _manualSpeedOverride.value ?: interval.targetSpeedMph
        val incline = _manualInclineOverride.value ?: interval.targetInclinePercent

        viewModelScope.launch {
            treadmillService.setSpeed(speed)
            treadmillService.setIncline(incline)
        }
    }

    private fun startIntervalTimer() {
        intervalJob?.cancel()
        intervalJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                val remaining = _intervalTimeRemaining.value - 1

                // Sample data for averages
                val currentState = treadmillService.state.value
                speedSamples.add(currentState.speedMph)
                inclineSamples.add(currentState.inclinePercent)

                if (remaining <= 0) {
                    // Move to next interval
                    val nextIndex = _currentIntervalIndex.value + 1
                    val template = _activeTemplate.value
                    if (template != null && nextIndex < template.intervals.size) {
                        _currentIntervalIndex.value = nextIndex
                        applyInterval(nextIndex)
                    } else {
                        // Workout complete
                        treadmillService.stopWorkout()
                        saveSession()
                        break
                    }
                } else {
                    _intervalTimeRemaining.value = remaining
                }
            }
        }
    }

    private suspend fun saveSession() {
        val sid = sessionId ?: return
        val state = treadmillService.state.value
        val session = workoutDao.getSession(sid) ?: return

        workoutDao.updateSession(session.copy(
            endedAt = System.currentTimeMillis(),
            durationSeconds = state.elapsedTimeSeconds,
            distanceMiles = state.distanceMiles,
            caloriesBurned = state.caloriesBurned,
            avgSpeedMph = if (speedSamples.isNotEmpty()) speedSamples.average() else 0.0,
            maxSpeedMph = speedSamples.maxOrNull() ?: 0.0,
            avgInclinePercent = if (inclineSamples.isNotEmpty()) inclineSamples.average() else 0.0,
            maxInclinePercent = inclineSamples.maxOrNull() ?: 0.0,
            avgHeartRateBpm = state.heartRateBpm,
            maxHeartRateBpm = state.heartRateBpm
        ))

        speedSamples.clear()
        inclineSamples.clear()
        sessionId = null
    }
}
