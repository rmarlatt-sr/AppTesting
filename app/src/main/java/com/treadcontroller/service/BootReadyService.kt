package com.treadcontroller.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

/**
 * Lightweight background service that starts on boot.
 *
 * What it does:
 * - Pre-connects to GlassOS (eru) so the app opens instantly
 * - Sits idle using minimal memory (~2-5 MB)
 * - Does NOT send any commands to the treadmill
 * - Does NOT interfere with iFit in any way
 *
 * What it does NOT do:
 * - No foreground notification (completely invisible)
 * - No workout tracking
 * - No speed/incline commands
 */
class BootReadyService : Service() {

    companion object {
        private const val TAG = "TreadController"
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var treadmillService: TreadmillService? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Boot service started — pre-connecting to GlassOS")

        scope.launch {
            try {
                // Check if we're on the treadmill (eru is installed)
                val isOnTreadmill = packageManager
                    .getLaunchIntentForPackage("com.ifit.eru") != null

                if (isOnTreadmill) {
                    val service = GlassOsService(applicationContext)
                    treadmillService = service
                    service.connect()
                    Log.i(TAG, "Pre-connected to GlassOS — ready for workout")
                } else {
                    Log.i(TAG, "Not on treadmill — boot service idle")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Boot pre-connect failed (non-critical): ${e.message}")
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        scope.launch {
            treadmillService?.disconnect()
        }
        scope.cancel()
        super.onDestroy()
    }
}
