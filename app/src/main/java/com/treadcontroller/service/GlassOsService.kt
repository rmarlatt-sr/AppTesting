package com.treadcontroller.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import com.treadcontroller.data.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import java.io.File
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Treadmill service for NordicTrack EXP 7i running iFit.
 *
 * Architecture:
 * - Connects to eru IPC for machine info (GetAllVersions)
 * - Monitors wolf log files (/sdcard/.wolflogs/) for real-time treadmill state
 *   (speed, incline, distance, time, calories) since the FitPro protocol data
 *   is logged there by standalone
 * - Control commands (speed, incline, workout) cannot be sent externally:
 *   - eru only exposes 9 utility IPC methods (no motor control)
 *   - gRPC services are InProcess only (not externally accessible)
 *   - Standalone's WolfIpcService crashes if external apps bind to it
 *   - The brainboard USB HID device is exclusively claimed by standalone
 *   - Physical console buttons communicate directly via USB HID to brainboard
 */
class GlassOsService(private val context: Context) : TreadmillService {
    companion object {
        private const val TAG = "GlassOS"
        private const val IPC_DATA_KEY = "IpcDataKey"
        private const val ERU_PACKAGE = "com.ifit.eru"
        private const val ERU_ACTION = "com.ifit.eru.IpcService"
        private const val WOLF_LOG_DIR = "/sdcard/.wolflogs"
        private const val LOG_POLL_INTERVAL_MS = 500L
    }

    private val _state = MutableStateFlow(TreadmillState())
    override val state: StateFlow<TreadmillState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isBound = false
    private var eruMessenger: Messenger? = null
    private var requestId = 0
    private var logMonitorJob: Job? = null

    // Handler for receiving replies from eru
    private val replyHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val data = msg.peekData() ?: return
            data.classLoader = context.classLoader
            val json = data.getString(IPC_DATA_KEY) ?: return
            handleIpcResponse(json)
        }
    }
    private val replyMessenger = Messenger(replyHandler)

    private fun handleIpcResponse(json: String) {
        try {
            val fixedJson = json
                .replace(":,", ":null,")
                .replace(":}", ":null}")
            val obj = JSONObject(fixedJson)
            val id = obj.optInt("Id", -1)
            val name = obj.optString("Name", "")
            val dataStr = if (obj.isNull("Data")) "" else obj.optString("Data", "")
            val error = if (obj.isNull("Error")) "" else obj.optString("Error", "")

            if (error.isNotEmpty()) {
                if (error != "Method not found") {
                    Log.w(TAG, "IPC error [id=$id, name=$name]: $error")
                }
                return
            }

            Log.i(TAG, "IPC response [id=$id, name=$name]: ${dataStr.take(200)}")

            when (name) {
                "GetAllVersions" -> handleVersions(dataStr)
                else -> Log.d(TAG, "Response: name=$name")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse IPC response: ${json.take(200)}", e)
        }
    }

    private fun handleVersions(data: String) {
        try {
            val obj = JSONObject(data)
            val eruVersion = obj.optString("EruVersion", "")
            val wolfVersion = obj.optString("WolfVersion", "")
            val ipAddress = obj.optString("IpAddress", "")
            val connectedMachine = obj.optInt("ConnectedMachineNumber", 0)
            Log.i(TAG, "Machine info: eru=$eruVersion, wolf=$wolfVersion, ip=$ipAddress, machine=$connectedMachine")

            _state.value = _state.value.copy(connectionState = ConnectionState.CONNECTED)

            // Start monitoring wolf logs after confirmed connection
            startLogMonitor()
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse versions: $data", e)
        }
    }

    private fun sendIpcMessage(methodName: String, dataJson: String = "{}"): Int {
        val messenger = eruMessenger ?: run {
            Log.w(TAG, "Cannot send IPC: not connected to eru")
            return -1
        }

        val id = requestId++
        val ipcObject = JSONObject().apply {
            put("Id", id)
            put("Name", methodName)
            put("Data", dataJson)
        }

        try {
            val msg = Message.obtain()
            msg.replyTo = replyMessenger
            msg.data = Bundle().apply {
                putString(IPC_DATA_KEY, ipcObject.toString())
            }
            messenger.send(msg)
            Log.d(TAG, "Sent IPC [id=$id]: $methodName")
        } catch (e: Exception) {
            Log.e(TAG, "sendIpcMessage failed for $methodName: ${e.message}", e)
        }
        return id
    }

    // --- Wolf Log Monitor ---
    // Reads /sdcard/.wolflogs/YYYY-MM-dd_logs.txt to extract real-time state

    private fun startLogMonitor() {
        if (logMonitorJob?.isActive == true) return

        logMonitorJob = scope.launch {
            Log.i(TAG, "Starting wolf log monitor")
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
            val logPath = "$WOLF_LOG_DIR/${today}_logs.txt"
            val logFile = File(logPath)

            if (!logFile.exists()) {
                Log.w(TAG, "Wolf log not found: $logPath")
                return@launch
            }

            // Read last 100KB for initial state
            try {
                val fileLen = logFile.length()
                val lookbackSize = 100_000L.coerceAtMost(fileLen)
                if (lookbackSize > 0) {
                    val raf = RandomAccessFile(logFile, "r")
                    raf.seek(fileLen - lookbackSize)
                    val lookbackBytes = ByteArray(lookbackSize.toInt())
                    val read = raf.read(lookbackBytes)
                    raf.close()
                    if (read > 0) {
                        parseLogLines(String(lookbackBytes, 0, read).lines())
                        val s = _state.value
                        Log.i(TAG, "Wolf lookback: speed=${String.format("%.1f", s.speedMph)}mph, incline=${s.inclinePercent}%, time=${s.elapsedTimeSeconds}s")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Wolf lookback error: ${e.message}")
            }

            // Use tail -f via Runtime.exec for real-time monitoring (bypasses FUSE caching)
            try {
                val process = Runtime.getRuntime().exec(arrayOf("tail", "-f", "-n", "0", logPath))
                val reader = process.inputStream.bufferedReader()
                Log.i(TAG, "Wolf tail -f started on $logPath")

                var lineBuffer = mutableListOf<String>()
                var lastFlush = System.currentTimeMillis()

                while (isActive) {
                    if (reader.ready()) {
                        val line = reader.readLine() ?: break
                        lineBuffer.add(line)

                        // Flush every 500ms or every 20 lines
                        val now = System.currentTimeMillis()
                        if (now - lastFlush >= 500 || lineBuffer.size >= 20) {
                            parseLogLines(lineBuffer)
                            lineBuffer = mutableListOf()
                            lastFlush = now
                        }
                    } else {
                        // Flush any buffered lines
                        if (lineBuffer.isNotEmpty()) {
                            parseLogLines(lineBuffer)
                            val s = _state.value
                            Log.d(TAG, "Wolf: speed=${String.format("%.1f", s.speedMph)}mph, incline=${s.inclinePercent}%, dist=${String.format("%.2f", s.distanceMiles)}mi, time=${s.elapsedTimeSeconds}s")
                            lineBuffer = mutableListOf()
                        }
                        delay(100)
                    }
                }

                process.destroy()
            } catch (e: Exception) {
                Log.e(TAG, "Wolf tail error: ${e.message}", e)
            }
        }
    }

    // Track meters for speed inference
    private var lastMeters = -1
    private var lastMetersTimeMs = 0L

    private fun parseLogLines(lines: List<String>) {
        var stateChanged = false
        var currentState = _state.value

        for (line in lines) {
            when {
                // Speed changes: [Trace:FitPro] Changed KPH to: 4.82
                line.contains("Changed KPH to:") -> {
                    val kph = extractDouble(line, "Changed KPH to:")
                    if (kph != null) {
                        val mph = kph / 1.60934
                        currentState = currentState.copy(speedMph = mph, targetSpeedMph = mph)
                        stateChanged = true
                    }
                }

                // Incline changes: [Trace:FitPro] Changed Grade to: 3
                line.contains("Changed Grade to:") -> {
                    val grade = extractDouble(line, "Changed Grade to:")
                    if (grade != null) {
                        currentState = currentState.copy(inclinePercent = grade, targetInclinePercent = grade)
                        stateChanged = true
                    }
                }

                // Distance: [Trace:Workout] SetMeters 76
                line.contains("SetMeters") -> {
                    val meters = extractInt(line, "SetMeters")
                    if (meters != null) {
                        val miles = meters / 1609.344
                        currentState = currentState.copy(distanceMiles = miles)
                        stateChanged = true

                        // Infer speed from distance change if no explicit speed set
                        val now = System.currentTimeMillis()
                        if (lastMeters >= 0 && lastMetersTimeMs > 0) {
                            val deltaMeters = meters - lastMeters
                            val deltaTimeS = (now - lastMetersTimeMs) / 1000.0
                            if (deltaTimeS > 0 && deltaMeters >= 0) {
                                val inferredKph = (deltaMeters / deltaTimeS) * 3.6
                                // Only update if no explicit speed was set or speed is still 0
                                if (currentState.speedMph == 0.0 && inferredKph > 0.1) {
                                    val mph = inferredKph / 1.60934
                                    currentState = currentState.copy(speedMph = mph)
                                }
                            }
                        }
                        lastMeters = meters
                        lastMetersTimeMs = now
                    }
                }

                // Time: [Trace:Workout] Set current time (r) to 150
                line.contains("Set current time (r) to") -> {
                    val seconds = extractInt(line, "Set current time (r) to")
                    if (seconds != null) {
                        currentState = currentState.copy(elapsedTimeSeconds = seconds)
                        stateChanged = true
                    }
                }

                // Workout state from FitnessConsole
                line.contains("WorkoutMode=Running") || line.contains("RequestData=Running") -> {
                    if (currentState.workoutState != WorkoutState.RUNNING) {
                        currentState = currentState.copy(workoutState = WorkoutState.RUNNING)
                        stateChanged = true
                        Log.i(TAG, "Workout state: RUNNING")
                    }
                }
                line.contains("WorkoutMode=WarmUp") || line.contains("RequestData=WarmUp") -> {
                    if (currentState.workoutState != WorkoutState.RUNNING) {
                        currentState = currentState.copy(workoutState = WorkoutState.RUNNING)
                        stateChanged = true
                        Log.i(TAG, "Workout state: WARMUP")
                    }
                }

                // Kph changed to 0 usually means workout ended/stopped
                line.contains("[Trace:FitnessConsole] Kph changed from") && line.contains("to 0") -> {
                    currentState = currentState.copy(speedMph = 0.0, workoutState = WorkoutState.IDLE)
                    stateChanged = true
                    Log.i(TAG, "Workout state: IDLE (speed dropped to 0)")
                }
            }
        }

        if (stateChanged) {
            _state.value = currentState
        }
    }

    private fun extractDouble(line: String, prefix: String): Double? {
        val idx = line.indexOf(prefix)
        if (idx == -1) return null
        val after = line.substring(idx + prefix.length).trim()
        return after.split("\\s".toRegex()).firstOrNull()?.toDoubleOrNull()
    }

    private fun extractInt(line: String, prefix: String): Int? {
        val idx = line.indexOf(prefix)
        if (idx == -1) return null
        val after = line.substring(idx + prefix.length).trim()
        return after.split("\\s".toRegex()).firstOrNull()?.toIntOrNull()
    }

    // --- Service Connection ---

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            Log.i(TAG, "Eru connected: $name")
            isBound = true

            if (binder != null) {
                eruMessenger = Messenger(binder)
                sendIpcMessage("GetAllVersions")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.w(TAG, "Eru disconnected")
            isBound = false
            eruMessenger = null
            _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
        }
    }

    override suspend fun connect() {
        _state.value = _state.value.copy(connectionState = ConnectionState.CONNECTING)
        Log.i(TAG, "Binding to eru IPC service...")

        val intent = Intent(ERU_ACTION).apply {
            setPackage(ERU_PACKAGE)
        }

        try {
            val bound = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            Log.i(TAG, "bindService returned: $bound")
            if (!bound) {
                Log.e(TAG, "Failed to bind to eru service")
                _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
            }
        } catch (e: Exception) {
            Log.e(TAG, "bindService exception: ${e.message}", e)
            _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
        }
    }

    override suspend fun disconnect() {
        logMonitorJob?.cancel()
        logMonitorJob = null

        if (isBound) {
            try { context.unbindService(serviceConnection) } catch (_: Exception) {}
            isBound = false
        }
        eruMessenger = null
        _state.value = _state.value.copy(connectionState = ConnectionState.DISCONNECTED)
    }

    // Control methods - log the intent but cannot actually control hardware.
    // Speed/incline are controlled via physical console buttons which communicate
    // directly with the brainboard (ICON Fitness USB HID device) via the FitPro protocol.
    // The USB device is exclusively claimed by standalone.

    override suspend fun startWorkout() {
        Log.i(TAG, "startWorkout requested (control not available - use iFit)")
    }

    override suspend fun pauseWorkout() {
        Log.i(TAG, "pauseWorkout requested (control not available - use iFit)")
    }

    override suspend fun resumeWorkout() {
        Log.i(TAG, "resumeWorkout requested (control not available - use iFit)")
    }

    override suspend fun stopWorkout() {
        Log.i(TAG, "stopWorkout requested (control not available - use iFit)")
    }

    override suspend fun setSpeed(mph: Double) {
        Log.i(TAG, "setSpeed($mph mph) requested (control not available - use physical buttons)")
    }

    override suspend fun setIncline(percent: Double) {
        Log.i(TAG, "setIncline($percent%) requested (control not available - use physical buttons)")
    }

    override suspend fun setFan(state: FanState) {
        Log.i(TAG, "setFan($state) requested (control not available)")
    }
}
