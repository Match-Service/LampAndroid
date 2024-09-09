package com.devndev.lamp.presentation.ui.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AuthManager {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun updateLoginStatus(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }

    fun updateLoadingStatus(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}
