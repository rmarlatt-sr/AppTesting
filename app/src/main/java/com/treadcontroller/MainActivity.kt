package com.treadcontroller

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.treadcontroller.data.db.AppDatabase
import com.treadcontroller.service.MockTreadmillService
import com.treadcontroller.service.TreadmillService
import com.treadcontroller.ui.screens.*
import com.treadcontroller.ui.theme.*
import com.treadcontroller.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fix: iFit's eru overlay window sits above our app, so all touch events
        // arrive with FLAG_WINDOW_IS_OBSCURED. Android views with the default
        // filterTouchesWhenObscured=true silently DROP these touches.
        // Recursively disable this on ALL views, including Compose's internal ones.
        val decorView = window.decorView
        disableObscuredTouchFilter(decorView)
        decorView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    disableObscuredTouchFilter(decorView)
                }
            }
        )

        val db = AppDatabase.getInstance(this)
        val dao = db.workoutDao()

        // Use mock service for development, GlassOS service on treadmill
        val treadmillService: TreadmillService = MockTreadmillService()
        // TODO: if (isOnTreadmill) GlassOsService(this) else MockTreadmillService()

        val dashboardViewModel = DashboardViewModel(treadmillService, applicationContext)
        val workoutBuilderViewModel = WorkoutBuilderViewModel(dao)
        val activeWorkoutViewModel = ActiveWorkoutViewModel(treadmillService, dao)
        val historyViewModel = HistoryViewModel(dao)

        setContent {
            TreadControllerTheme {
                TreadControllerApp(
                    dashboardViewModel = dashboardViewModel,
                    workoutBuilderViewModel = workoutBuilderViewModel,
                    activeWorkoutViewModel = activeWorkoutViewModel,
                    historyViewModel = historyViewModel
                )
            }
        }
    }

    private fun disableObscuredTouchFilter(view: View) {
        view.filterTouchesWhenObscured = false
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                disableObscuredTouchFilter(view.getChildAt(i))
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) return super.dispatchTouchEvent(ev)

        // iFit's eru overlay causes all events to have FLAG_WINDOW_IS_OBSCURED.
        // Strip this flag by creating a clean copy of the event.
        if (ev.flags and MotionEvent.FLAG_WINDOW_IS_OBSCURED != 0) {
            val clean = MotionEvent.obtain(
                ev.downTime, ev.eventTime, ev.action,
                ev.x, ev.y, ev.pressure, ev.size,
                ev.metaState, ev.xPrecision, ev.yPrecision,
                ev.deviceId, ev.edgeFlags
            )
            try {
                return super.dispatchTouchEvent(clean)
            } finally {
                clean.recycle()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}

enum class Screen(val route: String, val label: String, val icon: ImageVector) {
    Dashboard("dashboard", "Dashboard", Icons.Default.Home),
    Builder("builder", "Builder", Icons.Default.Add),
    Workout("workout", "Workout", Icons.Default.PlayArrow),
    History("history", "History", Icons.Default.List)
}

@Composable
fun TreadControllerApp(
    dashboardViewModel: DashboardViewModel,
    workoutBuilderViewModel: WorkoutBuilderViewModel,
    activeWorkoutViewModel: ActiveWorkoutViewModel,
    historyViewModel: HistoryViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            NavigationBar(containerColor = CardSurface) {
                Screen.values().forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Dashboard.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ElectricBlue,
                            selectedTextColor = ElectricBlue,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = ElectricBlue.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(dashboardViewModel)
            }
            composable(Screen.Builder.route) {
                WorkoutBuilderScreen(workoutBuilderViewModel) { template ->
                    activeWorkoutViewModel.loadTemplate(template)
                    navController.navigate(Screen.Workout.route) {
                        popUpTo(Screen.Dashboard.route) { saveState = true }
                        launchSingleTop = true
                    }
                }
            }
            composable(Screen.Workout.route) {
                ActiveWorkoutScreen(activeWorkoutViewModel)
            }
            composable(Screen.History.route) {
                HistoryScreen(historyViewModel)
            }
        }
    }
}
