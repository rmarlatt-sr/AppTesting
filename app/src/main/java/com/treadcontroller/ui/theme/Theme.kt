package com.treadcontroller.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ElectricBlue = Color(0xFF00D4FF)
val DarkBackground = Color(0xFF1A1A2E)
val DarkSurface = Color(0xFF16213E)
val CardSurface = Color(0xFF1F2B47)
val Teal = Color(0xFF0A9396)
val ErrorRed = Color(0xFFE63946)
val SuccessGreen = Color(0xFF2EC4B6)
val WarningAmber = Color(0xFFFFB703)
val TextPrimary = Color(0xFFE0E0E0)
val TextSecondary = Color(0xFF9E9E9E)

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = Teal,
    background = DarkBackground,
    surface = DarkSurface,
    error = ErrorRed,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = Color.White
)

@Composable
fun TreadControllerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
