package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.socket.LampSocketDataSource
import com.devndev.lamp.domain.repository.LampSocketRepository
import javax.inject.Inject

class LampSocketRepositoryImpl @Inject constructor(
    private val lampSocketDataSource: LampSocketDataSource
) : LampSocketRepository {
    override fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit) {
        lampSocketDataSource.connect(onConnected, onMessage)
    }

    override fun disconnect() {
        lampSocketDataSource.disconnect()
    }
}
