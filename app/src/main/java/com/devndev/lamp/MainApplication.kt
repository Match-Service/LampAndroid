package com.devndev.lamp

import android.app.Application
import com.devndev.lamp.data.socket.LampSocketService
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var lampSocketService: LampSocketService

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        lampSocketService.disconnect()
        super.onTerminate()
    }
}
