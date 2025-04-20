package com.devndev.lamp.data.datsource.socket

interface LampSocketDataSource {
    fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit)
    fun disconnect()
}
