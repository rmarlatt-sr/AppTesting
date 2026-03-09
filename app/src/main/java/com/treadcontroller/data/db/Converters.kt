package com.treadcontroller.data.db

import androidx.room.TypeConverter
import com.treadcontroller.data.model.WorkoutInterval
import org.json.JSONArray
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromIntervalList(intervals: List<WorkoutInterval>): String {
        val arr = JSONArray()
        intervals.forEach { interval ->
            arr.put(JSONObject().apply {
                put("durationSeconds", interval.durationSeconds)
                put("targetSpeedMph", interval.targetSpeedMph)
                put("targetInclinePercent", interval.targetInclinePercent)
                put("label", interval.label)
            })
        }
        return arr.toString()
    }

    @TypeConverter
    fun toIntervalList(json: String): List<WorkoutInterval> {
        if (json.isEmpty()) return emptyList()
        val arr = JSONArray(json)
        return (0 until arr.length()).map { i ->
            val obj = arr.getJSONObject(i)
            WorkoutInterval(
                durationSeconds = obj.getInt("durationSeconds"),
                targetSpeedMph = obj.getDouble("targetSpeedMph"),
                targetInclinePercent = obj.getDouble("targetInclinePercent"),
                label = obj.optString("label", "")
            )
        }
    }

    @TypeConverter
    fun fromDoubleList(list: List<Double>): String {
        return JSONArray(list).toString()
    }

    @TypeConverter
    fun toDoubleList(json: String): List<Double> {
        if (json.isEmpty()) return emptyList()
        val arr = JSONArray(json)
        return (0 until arr.length()).map { arr.getDouble(it) }
    }
}
