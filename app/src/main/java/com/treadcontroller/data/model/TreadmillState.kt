package com.treadcontroller.data.model

data class TreadmillState(
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val workoutState: WorkoutState = WorkoutState.IDLE,
    val speedMph: Double = 0.0,
    val targetSpeedMph: Double = 0.0,
    val inclinePercent: Double = 0.0,
    val targetInclinePercent: Double = 0.0,
    val elapsedTimeSeconds: Int = 0,
    val distanceMiles: Double = 0.0,
    val caloriesBurned: Double = 0.0,
    val heartRateBpm: Int = 0,
    val fanState: FanState = FanState.OFF,
    val maxSpeedMph: Double = 12.0,
    val minSpeedMph: Double = 0.0,
    val maxInclinePercent: Double = 15.0,
    val minInclinePercent: Double = -3.0
)

enum class ConnectionState {
    DISCONNECTED, CONNECTING, CONNECTED, ERROR
}

enum class WorkoutState {
    IDLE, RUNNING, PAUSED, COOLDOWN, RESULTS
}

enum class FanState {
    OFF, LOW, MEDIUM, HIGH, AUTO
}
