package com.devndev.lamp.domain.repository

interface LampSocketRepository {
    fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit)
    fun disconnect()
}
