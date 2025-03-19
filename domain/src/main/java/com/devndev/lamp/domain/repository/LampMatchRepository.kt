package com.devndev.lamp.domain.repository

interface LampMatchRepository {
    suspend fun startMatch()
    suspend fun stopMatch()
}
