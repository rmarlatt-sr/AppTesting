package com.treadcontroller.data

import com.treadcontroller.data.model.WorkoutInterval
import com.treadcontroller.data.model.WorkoutTemplate

data class MarathonWeek(
    val weekNumber: Int,
    val phase: String,
    val description: String,
    val workouts: List<WorkoutTemplate>
)

object CouchToMarathonProgram {

    val weeks: List<MarathonWeek> = listOf(
        week1(), week2(), week3(), week4(), week5(), week6(),
        week7(), week8(), week9(), week10(), week11(), week12(),
        week13(), week14(), week15(), week16(), week17(), week18(),
        week19(), week20(), week21(), week22(), week23(), week24()
    )

    fun allWorkouts(): List<WorkoutTemplate> = weeks.flatMap { it.workouts }

    fun getWeek(n: Int): List<WorkoutTemplate> =
        weeks.firstOrNull { it.weekNumber == n }?.workouts ?: emptyList()

    // ── Helper Functions ──────────────────────────────────────────────────

    private fun warmup(minutes: Int, speedMph: Double, incline: Double = 0.0): WorkoutInterval =
        WorkoutInterval(minutes * 60, speedMph, incline, "Warmup")

    private fun cooldown(minutes: Int, speedMph: Double = 2.5, incline: Double = 0.0): WorkoutInterval =
        WorkoutInterval(minutes * 60, speedMph, incline, "Cooldown")

    private fun cooldownSec(seconds: Int, speedMph: Double = 2.5, incline: Double = 0.0): WorkoutInterval =
        WorkoutInterval(seconds, speedMph, incline, "Cooldown")

    private fun runWalkIntervals(
        count: Int,
        runSeconds: Int,
        runSpeed: Double,
        runIncline: Double,
        walkSeconds: Int,
        walkSpeed: Double,
        walkIncline: Double = 0.0,
        runLabel: String = "Run",
        walkLabel: String = "Walk Break"
    ): List<WorkoutInterval> {
        val intervals = mutableListOf<WorkoutInterval>()
        repeat(count) {
            intervals.add(WorkoutInterval(runSeconds, runSpeed, runIncline, runLabel))
            intervals.add(WorkoutInterval(walkSeconds, walkSpeed, walkIncline, walkLabel))
        }
        return intervals
    }

    private fun tempoIntervals(
        count: Int,
        tempoSeconds: Int,
        tempoSpeed: Double,
        tempoIncline: Double,
        recoverySeconds: Int,
        recoverySpeed: Double,
        recoveryIncline: Double = 0.0
    ): List<WorkoutInterval> {
        val intervals = mutableListOf<WorkoutInterval>()
        repeat(count) {
            intervals.add(WorkoutInterval(tempoSeconds, tempoSpeed, tempoIncline, "Tempo Push"))
            intervals.add(WorkoutInterval(recoverySeconds, recoverySpeed, recoveryIncline, "Recovery Jog"))
        }
        return intervals
    }

    // ══════════════════════════════════════════════════════════════════════
    // PHASE 1: Walk to Run (Weeks 1-6)
    // ══════════════════════════════════════════════════════════════════════

    // ── Week 1 ────────────────────────────────────────────────────────────
    // Run 4.0, Walk 3.0, Ratio 1:1.5 min, 25-30min
    private fun week1(): MarathonWeek = MarathonWeek(
        weekNumber = 1,
        phase = "Phase 1: Walk to Run",
        description = "First steps - gentle run/walk intervals to build habit",
        workouts = listOf(
            // W1D1: 25min — Run/Walk flat
            // 5 warmup + 8x(1 run + 1.5 walk) = 20 + 0 cooldown... 5+20=25
            WorkoutTemplate(
                id = 0,
                name = "C2M W1D1 - First Run/Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(8, 60, 4.0, 0.0, 90, 3.0).toTypedArray(),
                    // total so far: 5*60 + 8*(60+90) = 300+1200 = 1500s = 25min
                )
            ),
            // W1D2: 25min — Run/Walk with 1% incline on run
            WorkoutTemplate(
                id = 0,
                name = "C2M W1D2 - Run/Walk Hills",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(8, 60, 4.0, 1.0, 90, 3.0).toTypedArray()
                    // 300+1200 = 1500s = 25min
                )
            ),
            // W1D3: 30min — Longer run/walk (long run day)
            // 5 warmup + 9x(1 run + 1.5 walk) = 22.5min + 2.5 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W1D3 - Long Run/Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(9, 60, 4.0, 0.0, 90, 3.0).toTypedArray(),
                    cooldownSec(150, 2.5)
                    // 300 + 9*150 + 150 = 300+1350+150 = 1800s = 30min
                )
            ),
            // W1D4: 25min — Recovery walk
            WorkoutTemplate(
                id = 0,
                name = "C2M W1D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(15 * 60, 3.2, 1.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+900+300 = 1500s = 25min
                )
            )
        )
    )

    // ── Week 2 ────────────────────────────────────────────────────────────
    // Run 4.2, Walk 3.0, Ratio 1.5:1 min, 25-30min
    private fun week2(): MarathonWeek = MarathonWeek(
        weekNumber = 2,
        phase = "Phase 1: Walk to Run",
        description = "Increasing run segments, decreasing walk breaks",
        workouts = listOf(
            // W2D1: 28min — 5 warmup + 8x(1.5 run + 1 walk) = 20 + 3 cooldown = 28
            WorkoutTemplate(
                id = 0,
                name = "C2M W2D1 - Building Run Time",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(8, 90, 4.2, 0.0, 60, 3.0).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300 + 8*150 + 180 = 300+1200+180 = 1680s = 28min
                )
            ),
            // W2D2: 28min — Same with incline
            WorkoutTemplate(
                id = 0,
                name = "C2M W2D2 - Hills Run/Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(8, 90, 4.2, 1.0, 60, 3.0).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300+1200+180 = 1680s = 28min
                )
            ),
            // W2D3: 30min — 5 warmup + 9x(1.5 run + 1 walk) = 22.5 + 2.5 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W2D3 - Long Run/Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    *runWalkIntervals(9, 90, 4.2, 0.0, 60, 3.0).toTypedArray(),
                    cooldownSec(150, 2.5)
                    // 300 + 9*150 + 150 = 300+1350+150 = 1800s = 30min
                )
            ),
            // W2D4: 30min — Recovery walk
            WorkoutTemplate(
                id = 0,
                name = "C2M W2D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 3.2, 1.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 3 ────────────────────────────────────────────────────────────
    // Run 4.3, Walk 3.0, Ratio 2:1.5 min, 28-32min
    private fun week3(): MarathonWeek = MarathonWeek(
        weekNumber = 3,
        phase = "Phase 1: Walk to Run",
        description = "Longer run segments building aerobic base",
        workouts = listOf(
            // W3D1: 30min — 5 warmup + 6x(2 run + 1.5 walk) = 21 + 4 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W3D1 - Run/Walk Progression",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(6, 120, 4.3, 0.0, 90, 3.0).toTypedArray(),
                    cooldown(4, 2.5)
                    // 300 + 6*210 + 240 = 300+1260+240 = 1800s = 30min
                )
            ),
            // W3D2: 30min — Same with incline
            WorkoutTemplate(
                id = 0,
                name = "C2M W3D2 - Hill Run/Walk",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(6, 120, 4.3, 1.0, 90, 3.0).toTypedArray(),
                    cooldown(4, 2.5)
                    // 300+1260+240 = 1800s = 30min
                )
            ),
            // W3D3: 32min — 5 warmup + 7x(2 run + 1.5 walk) = 24.5 + 2.5 cooldown = 32
            WorkoutTemplate(
                id = 0,
                name = "C2M W3D3 - Long Run/Walk",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(7, 120, 4.3, 0.0, 90, 3.0).toTypedArray(),
                    cooldownSec(150, 2.5)
                    // 300 + 7*210 + 150 = 300+1470+150 = 1920s = 32min
                )
            ),
            // W3D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W3D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 3.3, 1.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 4 (Deload) ──────────────────────────────────────────────────
    // Run 4.3, Walk 3.0, Ratio 2:1.5 min, 25-30min (reduced volume)
    private fun week4(): MarathonWeek = MarathonWeek(
        weekNumber = 4,
        phase = "Phase 1: Walk to Run",
        description = "Deload week - recover and consolidate gains",
        workouts = listOf(
            // W4D1: 26min — 4 warmup + 5x(2 run + 1.5 walk) = 17.5 + 4.5 cooldown
            WorkoutTemplate(
                id = 0,
                name = "C2M W4D1 - Easy Run/Walk",
                intervals = listOf(
                    warmup(4, 3.0),
                    *runWalkIntervals(5, 120, 4.3, 0.0, 90, 3.0).toTypedArray(),
                    cooldownSec(270, 2.5)
                    // 240 + 5*210 + 270 = 240+1050+270 = 1560s = 26min
                )
            ),
            // W4D2: 26min — Same with incline
            WorkoutTemplate(
                id = 0,
                name = "C2M W4D2 - Easy Hill Run/Walk",
                intervals = listOf(
                    warmup(4, 3.0),
                    *runWalkIntervals(5, 120, 4.3, 1.0, 90, 3.0).toTypedArray(),
                    cooldownSec(270, 2.5)
                    // 240+1050+270 = 1560s = 26min
                )
            ),
            // W4D3: 25min — 5 warmup + 5x(2 run + 1.5 walk) = 17.5 + 2.5 cooldown = 25
            WorkoutTemplate(
                id = 0,
                name = "C2M W4D3 - Short Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(5, 120, 4.3, 0.0, 90, 3.0).toTypedArray(),
                    cooldownSec(150, 2.5)
                    // 300 + 5*210 + 150 = 300+1050+150 = 1500s = 25min
                )
            ),
            // W4D4: 25min — Easy recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W4D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(15 * 60, 3.3, 1.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+900+300 = 1500s = 25min
                )
            )
        )
    )

    // ── Week 5 ────────────────────────────────────────────────────────────
    // Run 4.5, Walk 3.0, Ratio 4:2 min, 30-35min
    private fun week5(): MarathonWeek = MarathonWeek(
        weekNumber = 5,
        phase = "Phase 1: Walk to Run",
        description = "Longer run intervals - 4 minutes at a time",
        workouts = listOf(
            // W5D1: 32min — 5 warmup + 4x(4 run + 2 walk) = 24 + 3 cooldown = 32
            WorkoutTemplate(
                id = 0,
                name = "C2M W5D1 - Extended Run Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(4, 240, 4.5, 0.0, 120, 3.0).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300 + 4*360 + 180 = 300+1440+180 = 1920s = 32min
                )
            ),
            // W5D2: 32min — Same with incline
            WorkoutTemplate(
                id = 0,
                name = "C2M W5D2 - Hill Run Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(4, 240, 4.5, 1.0, 120, 3.0).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300+1440+180 = 1920s = 32min
                )
            ),
            // W5D3: 35min — 5 warmup + 5x(4 run + 1.5 walk) = 27.5 + 2.5 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W5D3 - Long Run/Walk",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(5, 240, 4.5, 0.0, 90, 3.0).toTypedArray(),
                    cooldownSec(150, 2.5)
                    // 300 + 5*330 + 150 = 300+1650+150 = 2100s = 35min
                )
            ),
            // W5D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W5D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 3.5, 2.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 6 ────────────────────────────────────────────────────────────
    // Run 4.5, Walk 3.0, Ratio 5:2 min, 30-35min
    private fun week6(): MarathonWeek = MarathonWeek(
        weekNumber = 6,
        phase = "Phase 1: Walk to Run",
        description = "5-minute run segments - nearly continuous running",
        workouts = listOf(
            // W6D1: 30min — 5 warmup + 3x(5 run + 2 walk) = 21 + 4 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W6D1 - 5min Run Segments",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(3, 300, 4.5, 0.0, 120, 3.0).toTypedArray(),
                    cooldown(4, 2.5)
                    // 300 + 3*420 + 240 = 300+1260+240 = 1800s = 30min
                )
            ),
            // W6D2: 30min — Same with incline
            WorkoutTemplate(
                id = 0,
                name = "C2M W6D2 - 5min Hill Segments",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(3, 300, 4.5, 1.0, 120, 3.0).toTypedArray(),
                    cooldown(4, 2.5)
                    // 300+1260+240 = 1800s = 30min
                )
            ),
            // W6D3: 35min — 5 warmup + 4x(5 run + 1.5 walk) = 26 + 4 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W6D3 - Long Run/Walk",
                intervals = listOf(
                    warmup(5, 3.0),
                    *runWalkIntervals(4, 300, 4.5, 0.0, 90, 3.0).toTypedArray(),
                    cooldown(4, 2.5)
                    // 300 + 4*390 + 240 = 300+1560+240 = 2100s = 35min
                )
            ),
            // W6D4: 35min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W6D4 - Recovery Walk",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(25 * 60, 3.5, 2.0, "Easy Walk"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            )
        )
    )

    // ══════════════════════════════════════════════════════════════════════
    // PHASE 2: Build Endurance (Weeks 7-12)
    // ══════════════════════════════════════════════════════════════════════

    // ── Week 7 ────────────────────────────────────────────────────────────
    // Easy 5.0, Tempo 5.5, Long Run 40min, 35-40min
    private fun week7(): MarathonWeek = MarathonWeek(
        weekNumber = 7,
        phase = "Phase 2: Build Endurance",
        description = "Continuous running begins - building aerobic engine",
        workouts = listOf(
            // W7D1: 35min — Easy continuous run
            // 5 warmup + 25 easy run + 5 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W7D1 - First Continuous Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 5.0, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W7D2: 35min — Tempo intervals
            // 5 warmup + 4x(4min tempo + 2min recovery) = 24 + 3min jog + 3 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W7D2 - Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 240, 5.5, 2.0, 120, 4.5).toTypedArray(),
                    WorkoutInterval(3 * 60, 4.5, 1.0, "Easy Jog"),
                    cooldown(3, 2.5)
                    // 300 + 4*360 + 180 + 180 = 300+1440+180+180 = 2100s = 35min
                )
            ),
            // W7D3: 40min — Long run
            // 5 warmup + 30 easy + 5 cooldown = 40
            WorkoutTemplate(
                id = 0,
                name = "C2M W7D3 - 40min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(30 * 60, 5.0, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+1800+300 = 2400s = 40min
                )
            ),
            // W7D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W7D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.0, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 8 (Deload) ──────────────────────────────────────────────────
    // Easy 4.8, Tempo 5.2, Long Run 35min, 30-35min
    private fun week8(): MarathonWeek = MarathonWeek(
        weekNumber = 8,
        phase = "Phase 2: Build Endurance",
        description = "Deload week - easier paces, shorter duration",
        workouts = listOf(
            // W8D1: 30min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W8D1 - Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(20 * 60, 4.8, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            ),
            // W8D2: 30min — Light tempo
            // 5 warmup + 3x(3min tempo + 2min recovery) = 15 + 5 easy + 5 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W8D2 - Light Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 180, 5.2, 1.0, 120, 4.5).toTypedArray(),
                    WorkoutInterval(5 * 60, 4.5, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*300 + 300 + 300 = 300+900+300+300 = 1800s = 30min
                )
            ),
            // W8D3: 35min — Long run (reduced)
            WorkoutTemplate(
                id = 0,
                name = "C2M W8D3 - 35min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 4.8, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W8D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W8D4 - Recovery Walk/Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 3.8, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 9 ────────────────────────────────────────────────────────────
    // Easy 5.0, Tempo 5.5, Long Run 45min, 35-45min
    private fun week9(): MarathonWeek = MarathonWeek(
        weekNumber = 9,
        phase = "Phase 2: Build Endurance",
        description = "Building back up - pushing long run to 45 minutes",
        workouts = listOf(
            // W9D1: 35min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W9D1 - Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 5.0, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W9D2: 35min — Tempo intervals
            // 5 warmup + 4x(4min tempo + 2min recovery) = 24 + 3 easy + 3 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W9D2 - Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 240, 5.5, 2.0, 120, 4.5).toTypedArray(),
                    WorkoutInterval(3 * 60, 4.5, 1.0, "Easy Jog"),
                    cooldown(3, 2.5)
                    // 300 + 4*360 + 180 + 180 = 2100s = 35min
                )
            ),
            // W9D3: 45min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W9D3 - 45min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(35 * 60, 5.0, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+2100+300 = 2700s = 45min
                )
            ),
            // W9D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W9D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.0, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 10 ───────────────────────────────────────────────────────────
    // Easy 5.2, Tempo 5.8, Long Run 50min, 35-50min
    private fun week10(): MarathonWeek = MarathonWeek(
        weekNumber = 10,
        phase = "Phase 2: Build Endurance",
        description = "Picking up the pace and pushing distance",
        workouts = listOf(
            // W10D1: 35min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W10D1 - Easy Run with Hills",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.2, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.0, 3.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.2, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+300+600+300 = 2100s = 35min
                )
            ),
            // W10D2: 38min — Tempo intervals
            // 5 warmup + 4x(5min tempo + 2min recovery) = 28 + 5 cooldown = 38... adjust
            // 5 warmup + 4x(4min tempo + 2min recovery) = 24 + 4 easy + 5 cooldown = 38
            WorkoutTemplate(
                id = 0,
                name = "C2M W10D2 - Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 240, 5.8, 2.0, 120, 4.8).toTypedArray(),
                    WorkoutInterval(4 * 60, 4.8, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 4*360 + 240 + 300 = 300+1440+240+300 = 2280s = 38min
                )
            ),
            // W10D3: 50min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W10D3 - 50min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(40 * 60, 5.2, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+2400+300 = 3000s = 50min
                )
            ),
            // W10D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W10D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.2, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 11 ───────────────────────────────────────────────────────────
    // Easy 5.2, Tempo 5.8, Long Run 55min, 35-55min
    private fun week11(): MarathonWeek = MarathonWeek(
        weekNumber = 11,
        phase = "Phase 2: Build Endurance",
        description = "Peak endurance building week before deload",
        workouts = listOf(
            // W11D1: 38min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W11D1 - Easy Run with Hills",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.2, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.0, 3.0, "Hill"),
                    WorkoutInterval(4 * 60, 5.2, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.0, 3.0, "Hill"),
                    WorkoutInterval(6 * 60, 5.2, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+240+240+240+360+300 = 2280s = 38min
                )
            ),
            // W11D2: 38min — Tempo
            // 5 warmup + 5x(4min tempo + 2min recovery) = 30 + 3 cooldown = 38
            WorkoutTemplate(
                id = 0,
                name = "C2M W11D2 - Tempo Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(5, 240, 5.8, 2.5, 120, 4.8).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300 + 5*360 + 180 = 300+1800+180 = 2280s = 38min
                )
            ),
            // W11D3: 55min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W11D3 - 55min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(45 * 60, 5.2, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+2700+300 = 3300s = 55min
                )
            ),
            // W11D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W11D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.2, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 12 (Deload) ─────────────────────────────────────────────────
    // Easy 5.0, Tempo 5.5, Long Run 40min, 30-40min
    private fun week12(): MarathonWeek = MarathonWeek(
        weekNumber = 12,
        phase = "Phase 2: Build Endurance",
        description = "Deload week - recover before marathon prep phase",
        workouts = listOf(
            // W12D1: 30min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W12D1 - Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(20 * 60, 5.0, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            ),
            // W12D2: 30min — Light tempo
            // 5 warmup + 3x(4min tempo + 2min recovery) = 18 + 2 easy + 5 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W12D2 - Light Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 240, 5.5, 1.5, 120, 4.5).toTypedArray(),
                    WorkoutInterval(2 * 60, 4.5, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*360 + 120 + 300 = 300+1080+120+300 = 1800s = 30min
                )
            ),
            // W12D3: 40min — Long run (reduced)
            WorkoutTemplate(
                id = 0,
                name = "C2M W12D3 - 40min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(30 * 60, 5.0, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+1800+300 = 2400s = 40min
                )
            ),
            // W12D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W12D4 - Recovery Walk/Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.0, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ══════════════════════════════════════════════════════════════════════
    // PHASE 3: Marathon Prep (Weeks 13-20)
    // ══════════════════════════════════════════════════════════════════════

    // ── Week 13 ───────────────────────────────────────────────────────────
    // Easy 5.3, Tempo 6.0, Long Run 60min, 35-60min
    private fun week13(): MarathonWeek = MarathonWeek(
        weekNumber = 13,
        phase = "Phase 3: Marathon Prep",
        description = "Marathon prep begins - first 60-minute long run",
        workouts = listOf(
            // W13D1: 40min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W13D1 - Easy Run with Hills",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.3, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.0, 3.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.3, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.0, 2.0, "Hill"),
                    cooldown(5, 2.5)
                    // 300+600+300+600+300+300 = 2400s = 40min
                )
            ),
            // W13D2: 38min — Tempo run
            // 5 warmup + 5x(4min tempo + 2min recovery) = 30 + 3 cooldown = 38
            WorkoutTemplate(
                id = 0,
                name = "C2M W13D2 - Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(5, 240, 6.0, 2.5, 120, 5.0).toTypedArray(),
                    cooldown(3, 2.5)
                    // 300 + 5*360 + 180 = 300+1800+180 = 2280s = 38min
                )
            ),
            // W13D3: 60min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W13D3 - 60min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(50 * 60, 5.3, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+3000+300 = 3600s = 60min
                )
            ),
            // W13D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W13D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.3, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 14 ───────────────────────────────────────────────────────────
    // Easy 5.3, Tempo 6.0, Long Run 70min, 35-70min
    private fun week14(): MarathonWeek = MarathonWeek(
        weekNumber = 14,
        phase = "Phase 3: Marathon Prep",
        description = "Building long run to 70 minutes",
        workouts = listOf(
            // W14D1: 40min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W14D1 - Easy Hill Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(8 * 60, 5.3, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.0, 3.0, "Hill"),
                    WorkoutInterval(4 * 60, 5.3, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.0, 4.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.3, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+480+240+240+240+600+300 = 2400s = 40min
                )
            ),
            // W14D2: 40min — Tempo run
            // 5 warmup + 3x(6min tempo + 3min recovery) = 27 + 3 easy + 5 cooldown = 40
            WorkoutTemplate(
                id = 0,
                name = "C2M W14D2 - Long Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 360, 6.0, 2.5, 180, 5.0).toTypedArray(),
                    WorkoutInterval(3 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*540 + 180 + 300 = 300+1620+180+300 = 2400s = 40min
                )
            ),
            // W14D3: 70min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W14D3 - 70min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(60 * 60, 5.3, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+3600+300 = 4200s = 70min
                )
            ),
            // W14D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W14D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.3, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 15 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.2, Long Run 75min, 40-75min
    private fun week15(): MarathonWeek = MarathonWeek(
        weekNumber = 15,
        phase = "Phase 3: Marathon Prep",
        description = "Pace increase and 75-minute long run",
        workouts = listOf(
            // W15D1: 45min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W15D1 - Hilly Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 3.0, "Hill"),
                    WorkoutInterval(5 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 4.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+300+300+300+600+300 = 2700s = 45min
                )
            ),
            // W15D2: 40min — Tempo
            // 5 warmup + 3x(6min tempo + 2min recovery) = 24 + 6 easy + 5 cooldown = 40
            WorkoutTemplate(
                id = 0,
                name = "C2M W15D2 - Tempo Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 360, 6.2, 3.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(6 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*480 + 360 + 300 = 300+1440+360+300 = 2400s = 40min
                )
            ),
            // W15D3: 75min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W15D3 - 75min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(65 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+3900+300 = 4500s = 75min
                )
            ),
            // W15D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W15D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 16 (Deload) ─────────────────────────────────────────────────
    // Easy 5.2, Tempo 5.8, Long Run 55min, 35-55min
    private fun week16(): MarathonWeek = MarathonWeek(
        weekNumber = 16,
        phase = "Phase 3: Marathon Prep",
        description = "Deload week - mid-marathon-prep recovery",
        workouts = listOf(
            // W16D1: 35min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W16D1 - Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 5.2, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W16D2: 35min — Light tempo
            // 5 warmup + 3x(4min tempo + 2min recovery) = 18 + 7 easy + 5 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W16D2 - Light Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 240, 5.8, 2.0, 120, 4.8).toTypedArray(),
                    WorkoutInterval(7 * 60, 4.8, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*360 + 420 + 300 = 300+1080+420+300 = 2100s = 35min
                )
            ),
            // W16D3: 55min — Long run (reduced)
            WorkoutTemplate(
                id = 0,
                name = "C2M W16D3 - 55min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(45 * 60, 5.2, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+2700+300 = 3300s = 55min
                )
            ),
            // W16D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W16D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.2, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 17 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.2, Long Run 85min, 40-85min
    private fun week17(): MarathonWeek = MarathonWeek(
        weekNumber = 17,
        phase = "Phase 3: Marathon Prep",
        description = "Back to building - 85-minute long run",
        workouts = listOf(
            // W17D1: 45min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W17D1 - Hilly Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 3.0, "Hill"),
                    WorkoutInterval(5 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 4.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+300+300+300+600+300 = 2700s = 45min
                )
            ),
            // W17D2: 42min — Tempo
            // 5 warmup + 4x(5min tempo + 2min recovery) = 28 + 4 easy + 5 cooldown = 42
            WorkoutTemplate(
                id = 0,
                name = "C2M W17D2 - Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 300, 6.2, 3.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(4 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 4*420 + 240 + 300 = 300+1680+240+300 = 2520s = 42min
                )
            ),
            // W17D3: 85min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W17D3 - 85min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(75 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+4500+300 = 5100s = 85min
                )
            ),
            // W17D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W17D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 18 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.2, Long Run 95min, 40-95min
    private fun week18(): MarathonWeek = MarathonWeek(
        weekNumber = 18,
        phase = "Phase 3: Marathon Prep",
        description = "Pushing long run toward 100 minutes",
        workouts = listOf(
            // W18D1: 45min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W18D1 - Hill Strength Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(8 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.2, 4.0, "Hill"),
                    WorkoutInterval(4 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.2, 5.0, "Hill"),
                    WorkoutInterval(4 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(4 * 60, 5.2, 3.0, "Hill"),
                    WorkoutInterval(7 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+480+240+240+240+240+240+420+300 = 2700s = 45min
                )
            ),
            // W18D2: 42min — Tempo
            // 5 warmup + 4x(5min tempo + 2min recovery) = 28 + 4 easy + 5 cooldown = 42
            WorkoutTemplate(
                id = 0,
                name = "C2M W18D2 - Tempo Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 300, 6.2, 3.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(4 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300+1680+240+300 = 2520s = 42min
                )
            ),
            // W18D3: 95min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W18D3 - 95min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(85 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+5100+300 = 5700s = 95min
                )
            ),
            // W18D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W18D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 19 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.5, Long Run 105min, 40-105min
    private fun week19(): MarathonWeek = MarathonWeek(
        weekNumber = 19,
        phase = "Phase 3: Marathon Prep",
        description = "Near-peak volume - 105-minute long run",
        workouts = listOf(
            // W19D1: 45min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W19D1 - Hilly Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 4.0, "Hill"),
                    WorkoutInterval(5 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 3.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+300+300+300+600+300 = 2700s = 45min
                )
            ),
            // W19D2: 45min — Tempo
            // 5 warmup + 4x(6min tempo + 2min recovery) = 32 + 3 easy + 5 cooldown = 45
            WorkoutTemplate(
                id = 0,
                name = "C2M W19D2 - Long Tempo Intervals",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 360, 6.5, 3.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(3 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 4*480 + 180 + 300 = 300+1920+180+300 = 2700s = 45min
                )
            ),
            // W19D3: 105min — Long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W19D3 - 105min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(95 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+5700+300 = 6300s = 105min
                )
            ),
            // W19D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W19D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 20 (Deload) ─────────────────────────────────────────────────
    // Easy 5.2, Tempo 6.0, Long Run 70min, 35-70min
    private fun week20(): MarathonWeek = MarathonWeek(
        weekNumber = 20,
        phase = "Phase 3: Marathon Prep",
        description = "Deload week - rest up before peak phase",
        workouts = listOf(
            // W20D1: 35min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W20D1 - Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 5.2, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W20D2: 35min — Light tempo
            // 5 warmup + 3x(4min tempo + 2min recovery) = 18 + 7 easy + 5 cooldown = 35
            WorkoutTemplate(
                id = 0,
                name = "C2M W20D2 - Light Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 240, 6.0, 2.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(7 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*360 + 420 + 300 = 300+1080+420+300 = 2100s = 35min
                )
            ),
            // W20D3: 70min — Long run (reduced)
            WorkoutTemplate(
                id = 0,
                name = "C2M W20D3 - 70min Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(60 * 60, 5.2, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+3600+300 = 4200s = 70min
                )
            ),
            // W20D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W20D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.2, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ══════════════════════════════════════════════════════════════════════
    // PHASE 4: Peak & Taper (Weeks 21-24)
    // ══════════════════════════════════════════════════════════════════════

    // ── Week 21 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.5, Long Run 120min (peak)
    private fun week21(): MarathonWeek = MarathonWeek(
        weekNumber = 21,
        phase = "Phase 4: Peak & Taper",
        description = "Peak week - longest run of the program at 120 minutes",
        workouts = listOf(
            // W21D1: 50min — Easy run with hills
            WorkoutTemplate(
                id = 0,
                name = "C2M W21D1 - Peak Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 3.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    WorkoutInterval(5 * 60, 5.2, 4.0, "Hill"),
                    WorkoutInterval(10 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+600+300+600+300+600+300 = 3000s = 50min
                )
            ),
            // W21D2: 45min — Tempo run
            // 5 warmup + 4x(6min tempo + 2min recovery) = 32 + 3 easy + 5 cooldown = 45
            WorkoutTemplate(
                id = 0,
                name = "C2M W21D2 - Peak Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(4, 360, 6.5, 3.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(3 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 4*480 + 180 + 300 = 300+1920+180+300 = 2700s = 45min
                )
            ),
            // W21D3: 120min — Peak long run
            WorkoutTemplate(
                id = 0,
                name = "C2M W21D3 - 120min Peak Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(110 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+6600+300 = 7200s = 120min
                )
            ),
            // W21D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W21D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 22 ───────────────────────────────────────────────────────────
    // Easy 5.5, Tempo 6.2, Long Run 90min (start taper)
    private fun week22(): MarathonWeek = MarathonWeek(
        weekNumber = 22,
        phase = "Phase 4: Peak & Taper",
        description = "Taper begins - reducing volume, maintaining fitness",
        workouts = listOf(
            // W22D1: 40min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W22D1 - Taper Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(30 * 60, 5.5, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1800+300 = 2400s = 40min
                )
            ),
            // W22D2: 38min — Tempo (reduced)
            // 5 warmup + 3x(5min tempo + 2min recovery) = 21 + 7 easy + 5 cooldown = 38
            WorkoutTemplate(
                id = 0,
                name = "C2M W22D2 - Taper Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(3, 300, 6.2, 2.5, 120, 5.0).toTypedArray(),
                    WorkoutInterval(7 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 3*420 + 420 + 300 = 300+1260+420+300 = 2280s = 38min
                )
            ),
            // W22D3: 90min — Long run (reduced from peak)
            WorkoutTemplate(
                id = 0,
                name = "C2M W22D3 - 90min Taper Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(80 * 60, 5.5, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+4800+300 = 5400s = 90min
                )
            ),
            // W22D4: 30min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W22D4 - Recovery Jog",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(20 * 60, 4.5, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            )
        )
    )

    // ── Week 23 ───────────────────────────────────────────────────────────
    // Easy 5.3, Tempo 6.0, Long Run 60min (heavy taper)
    private fun week23(): MarathonWeek = MarathonWeek(
        weekNumber = 23,
        phase = "Phase 4: Peak & Taper",
        description = "Heavy taper - significantly reduced volume, stay sharp",
        workouts = listOf(
            // W23D1: 35min — Easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W23D1 - Taper Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(25 * 60, 5.3, 1.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1500+300 = 2100s = 35min
                )
            ),
            // W23D2: 30min — Short tempo
            // 5 warmup + 2x(5min tempo + 2min recovery) = 14 + 6 easy + 5 cooldown = 30
            WorkoutTemplate(
                id = 0,
                name = "C2M W23D2 - Short Tempo",
                intervals = listOf(
                    warmup(5, 3.0),
                    *tempoIntervals(2, 300, 6.0, 2.0, 120, 5.0).toTypedArray(),
                    WorkoutInterval(6 * 60, 5.0, 1.0, "Easy Jog"),
                    cooldown(5, 2.5)
                    // 300 + 2*420 + 360 + 300 = 300+840+360+300 = 1800s = 30min
                )
            ),
            // W23D3: 60min — Long run (final long run before race)
            WorkoutTemplate(
                id = 0,
                name = "C2M W23D3 - Final Long Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(50 * 60, 5.3, 1.0, "Long Run"),
                    cooldown(5, 2.5)
                    // 300+3000+300 = 3600s = 60min
                )
            ),
            // W23D4: 25min — Recovery
            WorkoutTemplate(
                id = 0,
                name = "C2M W23D4 - Easy Recovery",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(15 * 60, 4.0, 0.0, "Recovery Jog"),
                    cooldown(5, 2.5)
                    // 300+900+300 = 1500s = 25min
                )
            )
        )
    )

    // ── Week 24 ───────────────────────────────────────────────────────────
    // Easy 5.0, Tempo 5.5, Long Run 30min easy (race week)
    private fun week24(): MarathonWeek = MarathonWeek(
        weekNumber = 24,
        phase = "Phase 4: Peak & Taper",
        description = "Race week - stay loose, trust your training, go get that marathon!",
        workouts = listOf(
            // W24D1: 25min — Short easy run
            WorkoutTemplate(
                id = 0,
                name = "C2M W24D1 - Race Week Easy",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(15 * 60, 5.0, 0.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+900+300 = 1500s = 25min
                )
            ),
            // W24D2: 20min — Short shakeout with strides
            // 5 warmup + 10 easy + 2x(30sec fast + 90sec easy) = 4min + 1min cooldown... let's keep simple
            // 5 warmup + 5 easy + 4x(30sec stride + 90sec jog) = 8min + 2 cooldown = 20
            WorkoutTemplate(
                id = 0,
                name = "C2M W24D2 - Strides Shakeout",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(5 * 60, 5.0, 0.0, "Easy Run"),
                    WorkoutInterval(30, 6.5, 0.0, "Stride"),
                    WorkoutInterval(90, 4.5, 0.0, "Recovery Jog"),
                    WorkoutInterval(30, 6.5, 0.0, "Stride"),
                    WorkoutInterval(90, 4.5, 0.0, "Recovery Jog"),
                    WorkoutInterval(30, 6.5, 0.0, "Stride"),
                    WorkoutInterval(90, 4.5, 0.0, "Recovery Jog"),
                    WorkoutInterval(30, 6.5, 0.0, "Stride"),
                    WorkoutInterval(90, 4.5, 0.0, "Recovery Jog"),
                    cooldown(2, 2.5)
                    // 300+300+30+90+30+90+30+90+30+90+120 = 1200s = 20min
                )
            ),
            // W24D3: 30min — Last run before race day
            WorkoutTemplate(
                id = 0,
                name = "C2M W24D3 - Pre-Race Easy Run",
                intervals = listOf(
                    warmup(5, 3.0),
                    WorkoutInterval(20 * 60, 5.0, 0.0, "Easy Run"),
                    cooldown(5, 2.5)
                    // 300+1200+300 = 1800s = 30min
                )
            ),
            // W24D4: 15min — Pre-race shakeout (day before or morning of)
            WorkoutTemplate(
                id = 0,
                name = "C2M W24D4 - Pre-Race Shakeout",
                intervals = listOf(
                    warmup(5, 2.5),
                    WorkoutInterval(4 * 60, 4.5, 0.0, "Easy Jog"),
                    WorkoutInterval(30, 5.5, 0.0, "Stride"),
                    WorkoutInterval(90, 3.5, 0.0, "Walk Break"),
                    WorkoutInterval(30, 5.5, 0.0, "Stride"),
                    WorkoutInterval(90, 3.5, 0.0, "Walk Break"),
                    cooldownSec(120, 2.5)
                    // 300+240+30+90+30+90+120 = 900s = 15min
                )
            )
        )
    )
}
