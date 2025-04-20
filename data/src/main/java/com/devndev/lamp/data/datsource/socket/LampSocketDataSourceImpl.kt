package com.devndev.lamp.data.datsource.socket

import com.devndev.lamp.data.socket.LampSocketService
import javax.inject.Inject

class LampSocketDataSourceImpl @Inject constructor(
    private val lampSocketService: LampSocketService
) : LampSocketDataSource {
    override fun connect(
        onConnected: () -> Unit,
        onMessage: (String) -> Unit,
        onUpdatedMessage: () -> Unit
    ) {
        lampSocketService.connect(onConnected, onMessage, onUpdatedMessage)
    }

    override fun disconnect() {
        lampSocketService.disconnect()
    }
}
