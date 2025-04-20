package com.devndev.lamp.data.socket

interface LampSocketService {
    fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit)
    fun disconnect()
}
