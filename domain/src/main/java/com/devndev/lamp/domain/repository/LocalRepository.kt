package com.devndev.lamp.domain.repository

interface LocalRepository {
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, value: Boolean): Boolean
    suspend fun putLong(key: String, value: Long)
    suspend fun getLong(key: String, value: Long): Long
}
