package com.treadcontroller.data

import com.treadcontroller.data.model.WorkoutInterval
import com.treadcontroller.data.model.WorkoutTemplate

object PresetWorkouts {

    val all: List<WorkoutTemplate> = weightLoss() + hiit() + inclineWalk() + longDistanceRun()

    // ── WEIGHT LOSS ─────────────────────────────────────────────────────

    fun weightLoss(): List<WorkoutTemplate> = listOf(
        // 30 min
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 30min - Easy",
            intervals = listOf(
                WorkoutInterval(3 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(2 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 30min - Medium",
            intervals = listOf(
                WorkoutInterval(3 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 30min - Hard",
            intervals = listOf(
                WorkoutInterval(3 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(3 * 60, 5.5, 4.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 4.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 4.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 4.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 4.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 30min - Extreme",
            intervals = listOf(
                WorkoutInterval(3 * 60, 4.5, 2.0, "Warmup"),
                WorkoutInterval(3 * 60, 6.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 2.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        // 45 min
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 45min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(5 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 45min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 45min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 45min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.5, 2.0, "Warmup"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 6.5, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.5, 3.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        // 60 min
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 60min - Easy",
            intervals = listOf(
                WorkoutInterval(3 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(210, 3.5, 2.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.0, 1.0, "Recovery"),
                WorkoutInterval(2 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 60min - Medium",
            intervals = listOf(
                WorkoutInterval(3 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(210, 4.5, 3.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 3.5, 1.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 60min - Hard",
            intervals = listOf(
                WorkoutInterval(3 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(210, 5.5, 5.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 4.0, 2.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Weight Loss 60min - Extreme",
            intervals = listOf(
                WorkoutInterval(3 * 60, 4.5, 2.0, "Warmup"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(210, 7.0, 6.0, "Fat Burn"),
                WorkoutInterval(2 * 60, 5.0, 3.0, "Recovery"),
                WorkoutInterval(2 * 60, 3.0, 0.0, "Cooldown")
            )
        )
    )

    // ── HIIT ────────────────────────────────────────────────────────────

    fun hiit(): List<WorkoutTemplate> = listOf(
        // 30 min
        WorkoutTemplate(
            id = 0,
            name = "HIIT 30min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 30min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 30min - Hard",
            intervals = listOf(
                WorkoutInterval(4 * 60, 4.5, 1.0, "Warmup"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(45, 8.5, 3.0, "Sprint"),
                WorkoutInterval(75, 4.0, 1.0, "Recovery"),
                WorkoutInterval(6 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 30min - Extreme",
            intervals = listOf(
                WorkoutInterval(4 * 60, 5.0, 2.0, "Warmup"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(45, 10.0, 4.0, "Sprint"),
                WorkoutInterval(75, 4.5, 1.0, "Recovery"),
                WorkoutInterval(6 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        // 45 min
        WorkoutTemplate(
            id = 0,
            name = "HIIT 45min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(3 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 45min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(3 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 45min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.5, 1.0, "Warmup"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(4 * 60, 4.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 45min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.0, 2.0, "Warmup"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(50, 10.0, 5.0, "Sprint"),
                WorkoutInterval(70, 4.5, 1.0, "Recovery"),
                WorkoutInterval(4 * 60, 4.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        // 60 min
        WorkoutTemplate(
            id = 0,
            name = "HIIT 60min - Easy",
            intervals = listOf(
                WorkoutInterval(8 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 6.0, 1.0, "Sprint"),
                WorkoutInterval(90, 3.0, 0.0, "Recovery"),
                WorkoutInterval(4 * 60, 3.5, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 60min - Medium",
            intervals = listOf(
                WorkoutInterval(8 * 60, 4.0, 1.0, "Warmup"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(1 * 60, 7.5, 2.0, "Sprint"),
                WorkoutInterval(90, 3.5, 0.0, "Recovery"),
                WorkoutInterval(4 * 60, 3.5, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 60min - Hard",
            intervals = listOf(
                WorkoutInterval(6 * 60, 4.5, 1.0, "Warmup"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(50, 8.5, 3.0, "Sprint"),
                WorkoutInterval(70, 4.0, 1.0, "Recovery"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "HIIT 60min - Extreme",
            intervals = listOf(
                WorkoutInterval(6 * 60, 5.0, 2.0, "Warmup"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(50, 10.5, 5.0, "Sprint"),
                WorkoutInterval(70, 5.0, 1.0, "Recovery"),
                WorkoutInterval(5 * 60, 4.5, 0.0, "Cooldown"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown"),
                WorkoutInterval(4 * 60, 2.5, 0.0, "Cooldown")
            )
        )
    )

    // ── INCLINE WALK ────────────────────────────────────────────────────

    fun inclineWalk(): List<WorkoutTemplate> = listOf(
        // 30 min
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 30min - Easy",
            intervals = listOf(
                WorkoutInterval(4 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.0, 3.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.0, 7.0, "Peak"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Descent"),
                WorkoutInterval(3 * 60, 3.0, 7.0, "Peak"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Descent"),
                WorkoutInterval(3 * 60, 3.0, 3.0, "Descent"),
                WorkoutInterval(5 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 30min - Medium",
            intervals = listOf(
                WorkoutInterval(4 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.5, 5.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.5, 10.0, "Peak"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(3 * 60, 3.5, 10.0, "Peak"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(3 * 60, 3.5, 5.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 30min - Hard",
            intervals = listOf(
                WorkoutInterval(4 * 60, 3.5, 1.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 3.8, 12.0, "Peak"),
                WorkoutInterval(3 * 60, 3.5, 10.0, "Descent"),
                WorkoutInterval(3 * 60, 3.8, 12.0, "Peak"),
                WorkoutInterval(3 * 60, 3.5, 10.0, "Descent"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 30min - Extreme",
            intervals = listOf(
                WorkoutInterval(4 * 60, 3.5, 2.0, "Warmup"),
                WorkoutInterval(3 * 60, 4.0, 10.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 4.0, 12.0, "Hill Climb"),
                WorkoutInterval(3 * 60, 4.0, 15.0, "Peak"),
                WorkoutInterval(3 * 60, 4.0, 12.0, "Descent"),
                WorkoutInterval(3 * 60, 4.0, 15.0, "Peak"),
                WorkoutInterval(3 * 60, 4.0, 12.0, "Descent"),
                WorkoutInterval(3 * 60, 4.0, 10.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        // 45 min
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 45min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(5 * 60, 3.0, 3.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 3.0, 7.0, "Peak"),
                WorkoutInterval(5 * 60, 3.0, 5.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 7.0, "Peak"),
                WorkoutInterval(5 * 60, 3.0, 5.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 3.0, "Descent"),
                WorkoutInterval(5 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 45min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(5 * 60, 3.5, 5.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 3.5, 10.0, "Peak"),
                WorkoutInterval(5 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(5 * 60, 3.5, 10.0, "Peak"),
                WorkoutInterval(5 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(5 * 60, 3.5, 5.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 45min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 1.0, "Warmup"),
                WorkoutInterval(5 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 4.0, 12.0, "Peak"),
                WorkoutInterval(5 * 60, 3.8, 10.0, "Descent"),
                WorkoutInterval(5 * 60, 4.0, 12.0, "Peak"),
                WorkoutInterval(5 * 60, 3.8, 10.0, "Descent"),
                WorkoutInterval(5 * 60, 3.5, 8.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 45min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 2.0, "Warmup"),
                WorkoutInterval(5 * 60, 4.0, 10.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 4.0, 12.0, "Hill Climb"),
                WorkoutInterval(5 * 60, 4.2, 15.0, "Peak"),
                WorkoutInterval(5 * 60, 4.0, 12.0, "Descent"),
                WorkoutInterval(5 * 60, 4.2, 15.0, "Peak"),
                WorkoutInterval(5 * 60, 4.0, 12.0, "Descent"),
                WorkoutInterval(5 * 60, 4.0, 10.0, "Descent"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        // 60 min
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 60min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.0, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.0, 5.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.0, 2.0, "Recovery"),
                WorkoutInterval(5 * 60, 2.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 60min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 0.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.5, 8.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 3.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 60min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 1.0, "Warmup"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(3 * 60, 3.8, 10.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.5, 4.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Incline Walk 60min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 3.5, 2.0, "Warmup"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(3 * 60, 4.0, 13.0, "Hill Climb"),
                WorkoutInterval(2 * 60, 3.8, 5.0, "Recovery"),
                WorkoutInterval(5 * 60, 3.0, 0.0, "Cooldown")
            )
        )
    )

    // ── LONG DISTANCE RUN ───────────────────────────────────────────────

    fun longDistanceRun(): List<WorkoutTemplate> = listOf(
        // 30 min
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 30min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 0.0, "Warmup"),
                WorkoutInterval(20 * 60, 5.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 30min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.5, 0.0, "Warmup"),
                WorkoutInterval(20 * 60, 6.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 30min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.0, 1.0, "Warmup"),
                WorkoutInterval(5 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 7.0, 2.0, "Push"),
                WorkoutInterval(5 * 60, 7.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 30min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.5, 1.0, "Warmup"),
                WorkoutInterval(5 * 60, 7.5, 2.0, "Steady Run"),
                WorkoutInterval(5 * 60, 8.0, 2.0, "Push"),
                WorkoutInterval(5 * 60, 8.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 7.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown")
            )
        ),
        // 45 min
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 45min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 0.0, "Warmup"),
                WorkoutInterval(35 * 60, 5.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 45min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.5, 0.0, "Warmup"),
                WorkoutInterval(5 * 60, 5.5, 1.0, "Steady Run"),
                WorkoutInterval(10 * 60, 6.0, 1.0, "Steady Run"),
                WorkoutInterval(10 * 60, 6.0, 2.0, "Push"),
                WorkoutInterval(10 * 60, 5.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 45min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.0, 1.0, "Warmup"),
                WorkoutInterval(8 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(8 * 60, 7.0, 2.0, "Push"),
                WorkoutInterval(8 * 60, 7.0, 1.0, "Steady Run"),
                WorkoutInterval(8 * 60, 6.5, 2.0, "Push"),
                WorkoutInterval(3 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 45min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.5, 1.0, "Warmup"),
                WorkoutInterval(8 * 60, 7.5, 2.0, "Steady Run"),
                WorkoutInterval(9 * 60, 8.0, 2.0, "Push"),
                WorkoutInterval(9 * 60, 8.0, 1.0, "Steady Run"),
                WorkoutInterval(9 * 60, 7.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown")
            )
        ),
        // 60 min
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 60min - Easy",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.0, 0.0, "Warmup"),
                WorkoutInterval(50 * 60, 5.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 60min - Medium",
            intervals = listOf(
                WorkoutInterval(5 * 60, 4.5, 0.0, "Warmup"),
                WorkoutInterval(10 * 60, 5.5, 1.0, "Steady Run"),
                WorkoutInterval(15 * 60, 6.0, 1.0, "Steady Run"),
                WorkoutInterval(15 * 60, 6.0, 2.0, "Push"),
                WorkoutInterval(10 * 60, 5.5, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 3.5, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 60min - Hard",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.0, 1.0, "Warmup"),
                WorkoutInterval(12 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(12 * 60, 7.0, 2.0, "Push"),
                WorkoutInterval(12 * 60, 7.0, 2.0, "Push"),
                WorkoutInterval(12 * 60, 6.5, 1.0, "Steady Run"),
                WorkoutInterval(7 * 60, 4.0, 0.0, "Cooldown")
            )
        ),
        WorkoutTemplate(
            id = 0,
            name = "Long Distance Run 60min - Extreme",
            intervals = listOf(
                WorkoutInterval(5 * 60, 5.5, 1.0, "Warmup"),
                WorkoutInterval(12 * 60, 7.5, 2.0, "Steady Run"),
                WorkoutInterval(13 * 60, 8.0, 2.0, "Push"),
                WorkoutInterval(13 * 60, 8.5, 2.0, "Push"),
                WorkoutInterval(12 * 60, 8.0, 1.0, "Steady Run"),
                WorkoutInterval(5 * 60, 4.0, 0.0, "Cooldown")
            )
        )
    )
}
