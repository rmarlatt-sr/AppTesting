package com.treadcontroller.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.treadcontroller.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Real GlassOS service implementation that communicates with
 * com.ifit.eru via Android IPC bound service.
 *
 * The eru service exposes its API at action "com.ifit.eru.IpcService"
 * using gRPC-style Protocol Buffer messages over Android Binder IPC.
 *
 * Available gRPC services (from proto definitions):
 * - SpeedService: SetSpeed(kph), GetSpeed, SpeedSubscription
 * - InclineService: SetIncline(percent), GetIncline, InclineSubscription
 * - WorkoutService: StartNewWorkout, Pause, Resume, Stop
 * - ConsoleService: Connect, Disconnect, GetConsoleState
 * - FanStateService: SetFanState, GetFanState
 * - ElapsedTimeService, DistanceService, CaloriesBurnedService, HeartRateService
 *
 * TODO: Implement IPC binding and proto message serialization
 */
class GlassOsService(private val context: Context) : TreadmillService {
    private val _state = MutableStateFlow(TreadmillState())
    override val state: StateFlow<TreadmillState> = _state.asStateFlow()

    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            isBound = true
            _state.value = _state.value.copy(connectionState = ConnectionState.CONNECTED)
            // TODO: Use binder to create gRPC channel and subscribe to streams
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
        }
    }

    override suspend fun connect() {
        _state.value = _state.value.copy(connectionState = ConnectionState.CONNECTING)
        val intent = Intent("com.ifit.eru.IpcService").apply {
            setPackage("com.ifit.eru")
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override suspend fun disconnect() {
        if (isBound) {
            context.unbindService(serviceConnection)
            isBound = false
        }
        _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
    }

    override suspend fun startWorkout() {
        // TODO: Call WorkoutService.StartNewWorkout() via IPC
    }

    override suspend fun pauseWorkout() {
        // TODO: Call WorkoutService.Pause() via IPC
    }

    override suspend fun resumeWorkout() {
        // TODO: Call WorkoutService.Resume() via IPC
    }

    override suspend fun stopWorkout() {
        // TODO: Call WorkoutService.Stop() via IPC
    }

    override suspend fun setSpeed(mph: Double) {
        // TODO: Convert mph to kph and call SpeedService.SetSpeed() via IPC
        // val kph = mph * 1.60934
    }

    override suspend fun setIncline(percent: Double) {
        // TODO: Call InclineService.SetIncline() via IPC
    }

    override suspend fun setFan(state: FanState) {
        // TODO: Call FanStateService.SetFanState() via IPC
    }
}
