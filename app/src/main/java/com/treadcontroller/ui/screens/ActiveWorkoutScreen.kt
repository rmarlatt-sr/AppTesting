package com.treadcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.treadcontroller.data.model.*
import com.treadcontroller.ui.theme.*
import com.treadcontroller.ui.util.KeepFrameClockAlive
import com.treadcontroller.ui.util.collectAsStatePolled
import com.treadcontroller.viewmodel.ActiveWorkoutViewModel

@Composable
fun ActiveWorkoutScreen(viewModel: ActiveWorkoutViewModel) {
    KeepFrameClockAlive()
    val state by viewModel.treadmillState.collectAsStatePolled()
    val currentIntervalIndex by viewModel.currentIntervalIndex.collectAsStatePolled()
    val intervalTimeRemaining by viewModel.intervalTimeRemaining.collectAsStatePolled()
    val template by viewModel.activeTemplate.collectAsStatePolled()

    val tmpl = template
    val intervals = tmpl?.intervals ?: emptyList()
    val currentInterval = intervals.getOrNull(currentIntervalIndex)
    val nextInterval = intervals.getOrNull(currentIntervalIndex + 1)
    val totalIntervals = intervals.size
    val hasTemplate = tmpl != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        Text(
            if (hasTemplate) tmpl!!.name else "No workout loaded",
            color = if (hasTemplate) TextPrimary else TextSecondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = if (hasTemplate) TextAlign.Start else TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Overall Progress
        Card(colors = CardDefaults.cardColors(containerColor = CardSurface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Overall Progress", color = TextSecondary, fontSize = 12.sp)
                    Text(
                        if (hasTemplate) "${currentIntervalIndex + 1} / $totalIntervals" else "-- / --",
                        color = ElectricBlue, fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { if (totalIntervals > 0) (currentIntervalIndex.toFloat() / totalIntervals) else 0f },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = ElectricBlue,
                    trackColor = DarkBackground
                )
            }
        }

        // Current Interval
        Card(colors = CardDefaults.cardColors(containerColor = ElectricBlue.copy(alpha = 0.1f)), shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    currentInterval?.label ?: if (hasTemplate) "Ready" else "Go to Builder to create a workout",
                    color = if (hasTemplate) ElectricBlue else TextSecondary,
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Countdown
                Text(
                    formatTime(intervalTimeRemaining),
                    color = if (intervalTimeRemaining in 1..10) WarningAmber else TextPrimary,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )

                // Interval progress bar
                val intervalProgress = if (currentInterval != null && currentInterval.durationSeconds > 0) {
                    1f - (intervalTimeRemaining.toFloat() / currentInterval.durationSeconds)
                } else 0f
                LinearProgressIndicator(
                    progress = { intervalProgress.coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxWidth().height(6.dp).padding(top = 8.dp),
                    color = ElectricBlue,
                    trackColor = DarkBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Target values
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("TARGET SPEED", color = TextSecondary, fontSize = 10.sp)
                        Text(
                            "${currentInterval?.targetSpeedMph ?: "0.0"} mph",
                            color = ElectricBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold
                        )
                        Text("Current: ${String.format("%.1f", state.speedMph)}", color = TextSecondary, fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("TARGET INCLINE", color = TextSecondary, fontSize = 10.sp)
                        Text(
                            "${currentInterval?.targetInclinePercent ?: "0.0"}%",
                            color = SuccessGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold
                        )
                        Text("Current: ${String.format("%.1f", state.inclinePercent)}", color = TextSecondary, fontSize = 12.sp)
                    }
                }
            }
        }

        // Next Interval Preview
        Card(colors = CardDefaults.cardColors(containerColor = CardSurface), shape = RoundedCornerShape(12.dp)) {
            Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("NEXT: ", color = TextSecondary, fontSize = 12.sp)
                Text(
                    if (nextInterval != null) {
                        "${if (nextInterval.label.isNotBlank()) nextInterval.label + " - " else ""}${formatTime(nextInterval.durationSeconds)} | ${nextInterval.targetSpeedMph} mph | ${nextInterval.targetInclinePercent}%"
                    } else {
                        "—"
                    },
                    color = TextPrimary,
                    fontSize = 13.sp
                )
            }
        }

        // Stats row
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatCard(Modifier.weight(1f), "TIME", formatTime(state.elapsedTimeSeconds), ElectricBlue)
            StatCard(Modifier.weight(1f), "DISTANCE", String.format("%.2f mi", state.distanceMiles), Teal)
            StatCard(Modifier.weight(1f), "CALORIES", String.format("%.0f", state.caloriesBurned), WarningAmber)
            StatCard(Modifier.weight(1f), "HR", if (state.heartRateBpm > 0) "${state.heartRateBpm}" else "--", ErrorRed)
        }

        Spacer(modifier = Modifier.weight(1f))

        // Controls
        val buttonText = when (state.workoutState) {
            WorkoutState.RUNNING -> "PAUSE"
            WorkoutState.PAUSED -> "RESUME"
            else -> "START"
        }
        val buttonColor = when (state.workoutState) {
            WorkoutState.RUNNING -> WarningAmber
            else -> SuccessGreen
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    when (viewModel.treadmillState.value.workoutState) {
                        WorkoutState.RUNNING -> viewModel.pauseWorkout()
                        WorkoutState.PAUSED -> viewModel.resumeWorkout()
                        else -> viewModel.startWorkout()
                    }
                },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(12.dp),
                enabled = hasTemplate
            ) { Text(buttonText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black) }
            Button(
                onClick = { viewModel.stopWorkout() },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                shape = RoundedCornerShape(12.dp)
            ) { Text("STOP", fontSize = 18.sp, fontWeight = FontWeight.Bold) }
        }
    }
}
