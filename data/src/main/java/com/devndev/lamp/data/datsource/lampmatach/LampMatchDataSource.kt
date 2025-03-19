package com.devndev.lamp.data.datsource.lampmatach

interface LampMatchDataSource {
    suspend fun startMatch()
    suspend fun stopMatch()
}
