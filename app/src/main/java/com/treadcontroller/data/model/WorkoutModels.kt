package com.treadcontroller.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.treadcontroller.data.db.Converters

data class WorkoutInterval(
    val durationSeconds: Int,
    val targetSpeedMph: Double,
    val targetInclinePercent: Double,
    val label: String = ""
)

@Entity(tableName = "workout_templates")
@TypeConverters(Converters::class)
data class WorkoutTemplate(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val intervals: List<WorkoutInterval>,
    val createdAt: Long = System.currentTimeMillis()
) {
    val totalDurationSeconds: Int get() = intervals.sumOf { it.durationSeconds }
}

@Entity(tableName = "workout_sessions")
@TypeConverters(Converters::class)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val templateId: Long? = null,
    val templateName: String = "Manual Workout",
    val startedAt: Long = System.currentTimeMillis(),
    val endedAt: Long? = null,
    val durationSeconds: Int = 0,
    val distanceMiles: Double = 0.0,
    val caloriesBurned: Double = 0.0,
    val avgSpeedMph: Double = 0.0,
    val maxSpeedMph: Double = 0.0,
    val avgInclinePercent: Double = 0.0,
    val maxInclinePercent: Double = 0.0,
    val avgHeartRateBpm: Int = 0,
    val maxHeartRateBpm: Int = 0,
    val speedHistory: List<Double> = emptyList(),
    val inclineHistory: List<Double> = emptyList()
)
