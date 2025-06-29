package com.devndev.lamp.domain.repository

interface ConfigRepository {
    fun saveIsFirstOpen(isFirstOpen: Boolean)
    fun getIsFirstOpen(): Boolean
}
