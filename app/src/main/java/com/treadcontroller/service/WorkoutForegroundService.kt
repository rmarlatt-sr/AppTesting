package com.treadcontroller.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.treadcontroller.MainActivity
import com.treadcontroller.data.model.WorkoutState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

/**
 * Foreground service that keeps the workout engine alive when the user
 * switches to another app (Netflix, YouTube, etc.).
 *
 * Shows a persistent notification with current workout stats and
 * quick controls (pause/stop).
 */
class WorkoutForegroundService : Service() {

    companion object {
        const val CHANNEL_ID = "treadcontroller_workout"
        const val NOTIFICATION_ID = 1
        const val ACTION_PAUSE = "com.treadcontroller.PAUSE"
        const val ACTION_RESUME = "com.treadcontroller.RESUME"
        const val ACTION_STOP = "com.treadcontroller.STOP"

        fun start(context: Context) {
            val intent = Intent(context, WorkoutForegroundService::class.java)
            context.startForegroundService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, WorkoutForegroundService::class.java)
            context.stopService(intent)
        }
    }

    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var updateJob: Job? = null

    var treadmillService: TreadmillService? = null

    inner class LocalBinder : Binder() {
        fun getService(): WorkoutForegroundService = this@WorkoutForegroundService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PAUSE -> serviceScope.launch {
                treadmillService?.pauseWorkout()
            }
            ACTION_RESUME -> serviceScope.launch {
                treadmillService?.resumeWorkout()
            }
            ACTION_STOP -> serviceScope.launch {
                treadmillService?.stopWorkout()
                stopSelf()
            }
            else -> {
                startForeground(NOTIFICATION_ID, buildNotification(
                    status = "Starting...",
                    speed = "0.0",
                    incline = "0.0",
                    time = "00:00",
                    isPaused = false
                ))
                startUpdating()
            }
        }
        return START_STICKY
    }

    private fun startUpdating() {
        updateJob?.cancel()
        updateJob = serviceScope.launch {
            val service = treadmillService ?: return@launch
            service.state.collectLatest { state ->
                if (state.workoutState == WorkoutState.IDLE) {
                    stopSelf()
                    return@collectLatest
                }

                val minutes = state.elapsedTimeSeconds / 60
                val seconds = state.elapsedTimeSeconds % 60
                val timeStr = String.format("%02d:%02d", minutes, seconds)
                val isPaused = state.workoutState == WorkoutState.PAUSED

                val notification = buildNotification(
                    status = if (isPaused) "PAUSED" else "RUNNING",
                    speed = String.format("%.1f", state.speedMph),
                    incline = String.format("%.1f", state.inclinePercent),
                    time = timeStr,
                    isPaused = isPaused
                )
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    private fun buildNotification(
        status: String,
        speed: String,
        incline: String,
        time: String,
        isPaused: Boolean
    ): Notification {
        // Tap notification → open app
        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val openPending = PendingIntent.getActivity(
            this, 0, openIntent, PendingIntent.FLAG_IMMUTABLE
        )

        // Pause/Resume action
        val toggleIntent = Intent(this, WorkoutForegroundService::class.java).apply {
            action = if (isPaused) ACTION_RESUME else ACTION_PAUSE
        }
        val togglePending = PendingIntent.getService(
            this, 1, toggleIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val toggleLabel = if (isPaused) "Resume" else "Pause"
        val toggleIcon = if (isPaused) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause

        // Stop action
        val stopIntent = Intent(this, WorkoutForegroundService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPending = PendingIntent.getService(
            this, 2, stopIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentTitle("$status  |  $time")
            .setContentText("Speed: $speed mph  |  Incline: $incline%")
            .setContentIntent(openPending)
            .setOngoing(true)
            .setSilent(true)
            .addAction(toggleIcon, toggleLabel, togglePending)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop", stopPending)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Workout Tracking",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows workout status while using other apps"
            setShowBadge(false)
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        updateJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }
}
