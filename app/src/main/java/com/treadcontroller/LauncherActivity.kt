package com.treadcontroller

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.treadcontroller.ui.theme.*
import kotlinx.coroutines.delay

class LauncherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TreadControllerTheme {
                LauncherScreen(
                    onChooseTreadController = { launchTreadController() },
                    onChooseIFit = { launchIFit() }
                )
            }
        }
    }

    private fun launchTreadController() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun launchIFit() {
        // Try the iFit launcher first, then standalone, then arda
        val ifitPackages = listOf(
            "com.ifit.launcher",
            "com.ifit.standalone",
            "com.ifit.arda"
        )

        for (pkg in ifitPackages) {
            val intent = packageManager.getLaunchIntentForPackage(pkg)
            if (intent != null) {
                startActivity(intent)
                finish()
                return
            }
        }

        // Fallback: if no iFit found, just open TreadController
        launchTreadController()
    }
}

@Composable
fun LauncherScreen(
    onChooseTreadController: () -> Unit,
    onChooseIFit: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A1A),
                        DarkBackground,
                        Color(0xFF0A1628)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { it / 4 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.padding(32.dp)
            ) {
                // Title
                Text(
                    "Welcome",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    color = TextPrimary
                )
                Text(
                    "Choose your workout experience",
                    fontSize = 16.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Two big cards side by side
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // TreadController Card
                    LauncherCard(
                        modifier = Modifier.weight(1f),
                        title = "TreadController",
                        subtitle = "Custom workouts\nFull control\nWorkout history",
                        accentColor = ElectricBlue,
                        gradientColors = listOf(
                            ElectricBlue.copy(alpha = 0.15f),
                            ElectricBlue.copy(alpha = 0.05f)
                        ),
                        onClick = onChooseTreadController
                    )

                    // iFit Card
                    LauncherCard(
                        modifier = Modifier.weight(1f),
                        title = "iFit",
                        subtitle = "Trainer-led workouts\nGoogle Maps\nStreaming content",
                        accentColor = SuccessGreen,
                        gradientColors = listOf(
                            SuccessGreen.copy(alpha = 0.15f),
                            SuccessGreen.copy(alpha = 0.05f)
                        ),
                        onClick = onChooseIFit
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "You can switch anytime by swiping up for the app drawer",
                    fontSize = 12.sp,
                    color = TextSecondary.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun LauncherCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    accentColor: Color,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(220.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(gradientColors),
                    RoundedCornerShape(24.dp)
                )
                .then(
                    Modifier.background(
                        color = CardSurface.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(24.dp)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                // Accent line
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(accentColor, RoundedCornerShape(2.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    subtitle,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
