package com.devndev.lamp.presentation.ui.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TempStatus {
    private val _isWaiting = MutableStateFlow(false)
    val isWaiting: StateFlow<Boolean> get() = _isWaiting

    private val _profileName = MutableStateFlow<String?>(null)
    val profileName: StateFlow<String?> get() = _profileName

    fun updateIsWaiting(isWaiting: Boolean) {
        _isWaiting.value = isWaiting
    }

    fun updateProfileName(profileName: String?) {
        _profileName.value = profileName
    }
}
