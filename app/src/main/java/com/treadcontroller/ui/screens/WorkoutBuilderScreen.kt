package com.treadcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.treadcontroller.data.model.WorkoutInterval
import com.treadcontroller.data.model.WorkoutTemplate
import com.treadcontroller.ui.theme.*
import com.treadcontroller.viewmodel.WorkoutBuilderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutBuilderScreen(
    viewModel: WorkoutBuilderViewModel,
    onStartWorkout: (WorkoutTemplate) -> Unit
) {
    val templates by viewModel.templates.collectAsState(initial = emptyList())
    val currentName by viewModel.workoutName.collectAsState()
    val currentIntervals by viewModel.intervals.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var showSavedWorkouts by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header with toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (showSavedWorkouts) "Saved Workouts" else "Workout Builder",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { showSavedWorkouts = !showSavedWorkouts }) {
                Text(
                    if (showSavedWorkouts) "New Workout" else "Saved (${templates.size})",
                    color = ElectricBlue
                )
            }
        }

        // Single LazyColumn — content changes based on mode but structure stays stable
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (showSavedWorkouts) {
                items(templates, key = { it.id }) { template ->
                    SavedWorkoutCard(
                        template = template,
                        onLoad = {
                            viewModel.loadTemplate(template)
                            showSavedWorkouts = false
                        },
                        onStart = { onStartWorkout(template) },
                        onDelete = { viewModel.deleteTemplate(template) }
                    )
                }
            } else {
                // Workout Name
                item(key = "name_field") {
                    OutlinedTextField(
                        value = currentName,
                        onValueChange = { viewModel.setWorkoutName(it) },
                        label = { Text("Workout Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ElectricBlue,
                            unfocusedBorderColor = TextSecondary,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedLabelColor = ElectricBlue,
                            unfocusedLabelColor = TextSecondary
                        ),
                        singleLine = true
                    )
                }

                // Visual Preview
                if (currentIntervals.isNotEmpty()) {
                    item(key = "preview_chart") {
                        IntervalPreviewChart(currentIntervals)
                    }
                }

                // Interval list
                itemsIndexed(currentIntervals, key = { index, _ -> "interval_$index" }) { index, interval ->
                    IntervalCard(
                        index = index + 1,
                        interval = interval,
                        onDelete = { viewModel.removeInterval(index) }
                    )
                }

                // Add Interval Button
                item(key = "add_button") {
                    OutlinedButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(ElectricBlue.copy(alpha = 0.5f))
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = ElectricBlue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add Interval", color = ElectricBlue)
                    }
                }

                // Total Duration
                if (currentIntervals.isNotEmpty()) {
                    item(key = "total_duration") {
                        val totalSeconds = currentIntervals.sumOf { it.durationSeconds }
                        Text(
                            "Total: ${formatTime(totalSeconds)}",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Bottom Actions — always present, enabled when there are intervals
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.saveWorkout() },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = currentIntervals.isNotEmpty() && !showSavedWorkouts
            ) {
                Text("Save", color = if (currentIntervals.isNotEmpty() && !showSavedWorkouts) ElectricBlue else TextSecondary)
            }
            Button(
                onClick = {
                    val template = WorkoutTemplate(
                        name = currentName.ifBlank { "Custom Workout" },
                        intervals = currentIntervals
                    )
                    onStartWorkout(template)
                },
                modifier = Modifier.weight(1f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                shape = RoundedCornerShape(12.dp),
                enabled = currentIntervals.isNotEmpty() && !showSavedWorkouts
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = Color.Black)
                Text("Start", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Add Interval Dialog
    if (showAddDialog) {
        AddIntervalDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { interval ->
                viewModel.addInterval(interval)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun IntervalPreviewChart(intervals: List<WorkoutInterval>) {
    val maxSpeed = intervals.maxOf { it.targetSpeedMph }.coerceAtLeast(1.0)
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            val totalDuration = intervals.sumOf { it.durationSeconds }.toFloat()
            intervals.forEach { interval ->
                val widthFraction = interval.durationSeconds / totalDuration
                val heightFraction = (interval.targetSpeedMph / maxSpeed).toFloat()
                Box(
                    modifier = Modifier
                        .weight(widthFraction)
                        .fillMaxHeight(heightFraction.coerceIn(0.1f, 1f))
                        .background(ElectricBlue.copy(alpha = 0.7f), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                )
            }
        }
    }
}

@Composable
fun IntervalCard(index: Int, interval: WorkoutInterval, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = ElectricBlue.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "$index",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            if (interval.label.isNotBlank()) {
                Text(interval.label, color = TextPrimary, modifier = Modifier.weight(1f))
            }
            Column(modifier = if (interval.label.isBlank()) Modifier.weight(1f) else Modifier) {
                Text(
                    "${formatTime(interval.durationSeconds)}  |  ${interval.targetSpeedMph} mph  |  ${interval.targetInclinePercent}%",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
fun SavedWorkoutCard(
    template: WorkoutTemplate,
    onLoad: () -> Unit,
    onStart: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(template.name, color = TextPrimary, fontWeight = FontWeight.Medium)
                Text(
                    "${template.intervals.size} intervals  |  ${formatTime(template.totalDurationSeconds)}",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
            TextButton(onClick = onLoad) { Text("Edit", color = ElectricBlue) }
            IconButton(onClick = onStart) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = SuccessGreen)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed.copy(alpha = 0.5f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIntervalDialog(onDismiss: () -> Unit, onAdd: (WorkoutInterval) -> Unit) {
    var label by remember { mutableStateOf("") }
    var minutes by remember { mutableStateOf("1") }
    var seconds by remember { mutableStateOf("0") }
    var speed by remember { mutableStateOf("5.0") }
    var incline by remember { mutableStateOf("0.0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardSurface,
        title = { Text("Add Interval", color = TextPrimary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = ElectricBlue, unfocusedLabelColor = TextSecondary
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = minutes,
                        onValueChange = { minutes = it.filter { c -> c.isDigit() } },
                        label = { Text("Min") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                            focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextSecondary,
                            focusedLabelColor = ElectricBlue, unfocusedLabelColor = TextSecondary
                        )
                    )
                    OutlinedTextField(
                        value = seconds,
                        onValueChange = { seconds = it.filter { c -> c.isDigit() } },
                        label = { Text("Sec") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                            focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextSecondary,
                            focusedLabelColor = ElectricBlue, unfocusedLabelColor = TextSecondary
                        )
                    )
                }
                OutlinedTextField(
                    value = speed,
                    onValueChange = { speed = it },
                    label = { Text("Speed (mph)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = ElectricBlue, unfocusedLabelColor = TextSecondary
                    )
                )
                OutlinedTextField(
                    value = incline,
                    onValueChange = { incline = it },
                    label = { Text("Incline (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary,
                        focusedBorderColor = ElectricBlue, unfocusedBorderColor = TextSecondary,
                        focusedLabelColor = ElectricBlue, unfocusedLabelColor = TextSecondary
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val dur = (minutes.toIntOrNull() ?: 0) * 60 + (seconds.toIntOrNull() ?: 0)
                    if (dur > 0) {
                        onAdd(WorkoutInterval(
                            durationSeconds = dur,
                            targetSpeedMph = speed.toDoubleOrNull() ?: 5.0,
                            targetInclinePercent = incline.toDoubleOrNull() ?: 0.0,
                            label = label
                        ))
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) { Text("Add", color = Color.Black) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = TextSecondary) }
        }
    )
}
