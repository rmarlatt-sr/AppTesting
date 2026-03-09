package com.treadcontroller.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.treadcontroller.data.model.WorkoutSession
import com.treadcontroller.ui.theme.*
import com.treadcontroller.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(viewModel: HistoryViewModel) {
    val sessions by viewModel.sessions.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text("Workout History", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (sessions.isEmpty()) {
                item(key = "empty") {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No workouts yet.\nComplete a workout to see it here.", color = TextSecondary)
                    }
                }
            } else {
                items(sessions, key = { it.id }) { session ->
                    SessionCard(session, onDelete = { viewModel.deleteSession(session.id) })
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: WorkoutSession, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = CardSurface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(session.templateName, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Text(
                        dateFormat.format(Date(session.startedAt)),
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
                Text(
                    formatTime(session.durationSeconds),
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed.copy(alpha = 0.5f))
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = DarkBackground)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("Distance", String.format("%.2f mi", session.distanceMiles))
                    StatItem("Avg Speed", String.format("%.1f mph", session.avgSpeedMph))
                    StatItem("Max Speed", String.format("%.1f mph", session.maxSpeedMph))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem("Calories", String.format("%.0f", session.caloriesBurned))
                    StatItem("Avg Incline", String.format("%.1f%%", session.avgInclinePercent))
                    StatItem("Avg HR", if (session.avgHeartRateBpm > 0) "${session.avgHeartRateBpm} bpm" else "--")
                }
                if (session.endedAt != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Ended: ${dateFormat.format(Date(session.endedAt))}",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = TextSecondary, fontSize = 10.sp)
        Text(value, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}
