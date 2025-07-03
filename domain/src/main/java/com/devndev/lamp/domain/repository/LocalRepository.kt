package com.devndev.lamp.domain.repository

interface LocalRepository {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, value: Boolean): Boolean
}
