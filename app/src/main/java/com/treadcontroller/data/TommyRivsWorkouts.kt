package com.treadcontroller.data

import com.treadcontroller.data.model.WorkoutInterval
import com.treadcontroller.data.model.WorkoutTemplate

/**
 * Tommy Rivs — Iberian Beginner Running Series (Week 1)
 *
 * Approximated from iFit workout overview charts.
 * Pattern: Walk/Run intervals building beginner endurance.
 * Walk pace ~3.0 mph, Run pace ~4.8 mph.
 */
object TommyRivsWorkouts {

    val all: List<WorkoutTemplate> get() = listOf(
        iberianWeek1Workout2,
        iberianWeek1Workout3,
        iberianWeek1Workout4,
        iberianWeek1Workout5
    )

    /**
     * Iberian Beginner Running Series — Week 1, Workout 2
     * ~30 min | Max 4.8 mph | Avg 3.1 mph | Max incline 4% | Avg 0%
     *
     * Profile: Warmup walk, then run/walk intervals with gentle rolling hills
     * in first half (up to 4%), flattening out in second half.
     */
    val iberianWeek1Workout2 = WorkoutTemplate(
        name = "Tommy Rivs: Iberian Beginner W1D2",
        intervals = listOf(
            // Warmup
            WorkoutInterval(durationSeconds = 180, targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Warmup Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Warmup Walk"),

            // Block 1 — Rolling hills (incline 1-4%)
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 2.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 3.0, label = "Run — Hill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 4.0, label = "Run — Hill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 2.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 3.0, label = "Run — Hill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),

            // Block 2 — Flatter terrain
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),

            // Cooldown
            WorkoutInterval(durationSeconds = 120, targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Cooldown Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Cooldown")
        )
        // Total: 180+60 + 8*(45+75) + 4*(45+75) + 120+60 = 240 + 960 + 480 + 180 = 1860s = 31:00
    )

    /**
     * Iberian Beginner Running Series — Week 1, Workout 3
     * ~30 min | Max 4.8 mph | Avg 3.0 mph | Max incline 2% | Avg -0.3%
     *
     * Profile: Warmup, then run/walk intervals on mostly flat to slightly
     * negative terrain. Brief bumps to 2%, second half sustains 1-2%.
     */
    val iberianWeek1Workout3 = WorkoutTemplate(
        name = "Tommy Rivs: Iberian Beginner W1D3",
        intervals = listOf(
            // Warmup
            WorkoutInterval(durationSeconds = 180, targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Warmup Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Warmup Walk"),

            // Block 1 — Slightly negative / flat with small bumps
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = -1.0, label = "Run — Downhill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = -1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = -1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = -1.0, label = "Run — Downhill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),

            // Block 2 — Sustained gentle incline
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),

            // Cooldown
            WorkoutInterval(durationSeconds = 120, targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Cooldown Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Cooldown")
        )
        // Total: 240 + 5*(120) + 5*(120) + 4*(120) + 180 = 240 + 600 + 480 + 180 = ~31:00
    )

    /**
     * Iberian Beginner Running Series — Week 1, Workout 4
     * ~30 min | Max 4.8 mph | Avg 3.0 mph | Max incline 8% | Avg 0.1%
     *
     * Profile: Walk/run intervals with a significant hill section in the
     * middle (incline spikes to 6-8%). Rest is mostly flat.
     */
    val iberianWeek1Workout4 = WorkoutTemplate(
        name = "Tommy Rivs: Iberian Beginner W1D4",
        intervals = listOf(
            // Warmup
            WorkoutInterval(durationSeconds = 180, targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Warmup Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Warmup Walk"),

            // Block 1 — Flat intervals
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),

            // Block 2 — Hill section (the big climb)
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 3.0, label = "Run — Climb"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 5.0, label = "Walk — Hill"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 6.0, label = "Run — Big Hill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 8.0, label = "Walk — Summit"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 6.0, label = "Run — Descent"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 3.0, label = "Walk — Descent"),

            // Block 3 — Back to flat
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = -1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),

            // Cooldown
            WorkoutInterval(durationSeconds = 120, targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Cooldown Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Cooldown")
        )
        // Total: 240 + 12*(120) + 180 = 240 + 1440 + 180 = 1860s = 31:00
    )

    /**
     * Iberian Beginner Running Series — Week 1, Workout 5
     * ~30 min | Max 4.8 mph | Avg 3.0 mph | Max incline 2% | Avg 0.1%
     *
     * Profile: Walk/run intervals on gentle rolling terrain,
     * alternating between -1% and 2%.
     */
    val iberianWeek1Workout5 = WorkoutTemplate(
        name = "Tommy Rivs: Iberian Beginner W1D5",
        intervals = listOf(
            // Warmup
            WorkoutInterval(durationSeconds = 180, targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Warmup Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Warmup Walk"),

            // Block 1 — Gentle rolling
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = -1.0, label = "Run — Downhill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = -1.0, label = "Run — Downhill"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),

            // Block 2 — More rolling
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 1.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = -1.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 2.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Walk"),
            WorkoutInterval(durationSeconds = 45,  targetSpeedMph = 4.8, targetInclinePercent = 0.0, label = "Run"),
            WorkoutInterval(durationSeconds = 75,  targetSpeedMph = 3.0, targetInclinePercent = 1.0, label = "Walk"),

            // Cooldown
            WorkoutInterval(durationSeconds = 120, targetSpeedMph = 3.0, targetInclinePercent = 0.0, label = "Cooldown Walk"),
            WorkoutInterval(durationSeconds = 60,  targetSpeedMph = 2.5, targetInclinePercent = 0.0, label = "Cooldown")
        )
        // Total: 240 + 8*(120) + 4*(120) + 180 = 240 + 960 + 480 + 180 = 1860s = 31:00
    )
}
