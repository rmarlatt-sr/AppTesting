package com.treadcontroller.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Starts TreadController's background service when the treadmill boots up.
 * The service pre-connects to GlassOS so the app is ready instantly when opened.
 * It does NOT start a workout or send any commands — just establishes the connection.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, BootReadyService::class.java)
            context.startService(serviceIntent)
        }
    }
}
