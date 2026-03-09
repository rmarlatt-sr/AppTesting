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
import com.treadcontroller.data.model.FanState
import com.treadcontroller.data.model.WorkoutState
import com.treadcontroller.ui.theme.*
import com.treadcontroller.ui.util.KeepFrameClockAlive
import com.treadcontroller.ui.util.collectAsStatePolled
import com.treadcontroller.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    KeepFrameClockAlive()
    val state by viewModel.treadmillState.collectAsStatePolled()
    val workoutState = state.workoutState

    val primaryText = when (workoutState) {
        WorkoutState.RUNNING -> "PAUSE"
        WorkoutState.PAUSED -> "RESUME"
        else -> "START"
    }
    val primaryColor = when (workoutState) {
        WorkoutState.RUNNING -> WarningAmber
        else -> SuccessGreen
    }
    val showStop = workoutState == WorkoutState.RUNNING || workoutState == WorkoutState.PAUSED

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Status Badge
        StatusBadge(workoutState)

        // Main Metrics Row - Speed and Incline
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                label = "SPEED",
                value = String.format("%.1f", state.speedMph),
                unit = "mph",
                target = if (workoutState == WorkoutState.RUNNING)
                    String.format("%.1f", state.targetSpeedMph) else null,
                sliderValue = state.targetSpeedMph.toFloat(),
                sliderRange = state.minSpeedMph.toFloat()..state.maxSpeedMph.toFloat(),
                onSliderChange = { viewModel.setSpeed(it.toDouble()) },
                onIncrement = { viewModel.setSpeed(state.targetSpeedMph + 0.1) },
                onDecrement = { viewModel.setSpeed(state.targetSpeedMph - 0.1) },
                accentColor = ElectricBlue
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                label = "INCLINE",
                value = String.format("%.1f", state.inclinePercent),
                unit = "%",
                target = if (workoutState == WorkoutState.RUNNING)
                    String.format("%.1f", state.targetInclinePercent) else null,
                sliderValue = state.targetInclinePercent.toFloat(),
                sliderRange = state.minInclinePercent.toFloat()..state.maxInclinePercent.toFloat(),
                onSliderChange = { viewModel.setIncline(it.toDouble()) },
                onIncrement = { viewModel.setIncline(state.targetInclinePercent + 0.5) },
                onDecrement = { viewModel.setIncline(state.targetInclinePercent - 0.5) },
                accentColor = SuccessGreen
            )
        }

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(Modifier.weight(1f), "TIME", formatTime(state.elapsedTimeSeconds), ElectricBlue)
            StatCard(Modifier.weight(1f), "DISTANCE", String.format("%.2f mi", state.distanceMiles), Teal)
            StatCard(Modifier.weight(1f), "CALORIES", String.format("%.0f", state.caloriesBurned), WarningAmber)
            StatCard(Modifier.weight(1f), "HEART RATE", if (state.heartRateBpm > 0) "${state.heartRateBpm} bpm" else "--", ErrorRed)
        }

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    when (viewModel.treadmillState.value.workoutState) {
                        WorkoutState.RUNNING -> viewModel.pauseWorkout()
                        WorkoutState.PAUSED -> viewModel.resumeWorkout()
                        else -> viewModel.startWorkout()
                    }
                },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(primaryText, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Button(
                onClick = { viewModel.stopWorkout() },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (showStop) ErrorRed else ErrorRed.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = showStop
            ) {
                Text("STOP", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Fan Control
        FanControlRow(state.fanState) { viewModel.setFan(it) }
    }
}

@Composable
fun StatusBadge(workoutState: WorkoutState) {
    val (text, color) = when (workoutState) {
        WorkoutState.IDLE -> "IDLE" to TextSecondary
        WorkoutState.RUNNING -> "RUNNING" to SuccessGreen
        WorkoutState.PAUSED -> "PAUSED" to WarningAmber
        WorkoutState.COOLDOWN -> "COOLDOWN" to ElectricBlue
        WorkoutState.RESULTS -> "COMPLETE" to ElectricBlue
    }
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    unit: String,
    target: String?,
    sliderValue: Float,
    sliderRange: ClosedFloatingPointRange<Float>,
    onSliderChange: (Float) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    value,
                    color = accentColor,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(unit, color = TextSecondary, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))
            }
            if (target != null) {
                Text("Target: $target $unit", color = TextSecondary, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledIconButton(
                    onClick = onDecrement,
                    modifier = Modifier.size(36.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = DarkSurface)
                ) {
                    Text("-", fontSize = 20.sp, color = TextPrimary)
                }
                Slider(
                    value = sliderValue,
                    onValueChange = onSliderChange,
                    valueRange = sliderRange,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = accentColor,
                        activeTrackColor = accentColor
                    )
                )
                FilledIconButton(
                    onClick = onIncrement,
                    modifier = Modifier.size(36.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = DarkSurface)
                ) {
                    Text("+", fontSize = 20.sp, color = TextPrimary)
                }
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, label: String, value: String, accentColor: Color) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, color = accentColor, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun FanControlRow(currentState: FanState, onFanChange: (FanState) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("FAN", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            FanState.values().filter { it != FanState.AUTO }.forEach { fanState ->
                val isSelected = currentState == fanState
                FilledTonalButton(
                    onClick = { onFanChange(fanState) },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (isSelected) ElectricBlue.copy(alpha = 0.3f) else Color.Transparent
                    ),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        fanState.name,
                        fontSize = 11.sp,
                        color = if (isSelected) ElectricBlue else TextSecondary
                    )
                }
            }
        }
    }
}

fun formatTime(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
