package com.treadcontroller.ui.util

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.StateFlow

/**
 * Forces Compose's Recomposer to continuously request Choreographer frames.
 * On Android 9 (this treadmill), Choreographer stops delivering frames when
 * there's no user interaction, which freezes recomposition. An infinite
 * animation keeps the frame clock alive.
 *
 * Call this once at the top level of any screen that needs live updates.
 */
@Composable
fun KeepFrameClockAlive() {
    val infiniteTransition = rememberInfiniteTransition(label = "keepAlive")
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "keepAlive"
    )
}

/**
 * Collects a StateFlow as Compose State. On Android 9 where collectAsState()
 * doesn't trigger recomposition reliably, pair this with KeepFrameClockAlive()
 * to ensure the Recomposer keeps running.
 */
@Composable
fun <T> StateFlow<T>.collectAsStatePolled(): State<T> {
    // With KeepFrameClockAlive() active, standard collectAsState should work
    // because the Recomposer is continuously getting frame callbacks.
    // But we still poll manually as a safety net.
    val flow = this
    val state = remember { mutableStateOf(flow.value) }

    LaunchedEffect(flow) {
        // This snapshotFlow will re-read flow.value on every recomposition frame
        // triggered by KeepFrameClockAlive's animation
        kotlinx.coroutines.flow.flow {
            while (true) {
                emit(flow.value)
                kotlinx.coroutines.delay(50)
            }
        }.collect { newValue ->
            if (state.value != newValue) {
                state.value = newValue
            }
        }
    }

    return state
}
