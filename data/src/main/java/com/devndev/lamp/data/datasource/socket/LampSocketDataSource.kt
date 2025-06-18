package com.devndev.lamp.data.datasource.socket

interface LampSocketDataSource {
    fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit)
    fun disconnect()
}
