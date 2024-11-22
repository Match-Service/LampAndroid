package com.devndev.lamp.presentation.ui.login

import androidx.compose.runtime.mutableIntStateOf
import com.devndev.lamp.presentation.ui.common.AccountStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AuthManager {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _accountStatus = mutableIntStateOf(AccountStatus.NONE)
    val accountStatus = _accountStatus

    fun updateLoginStatus(isLoggedIn: Boolean) {
        _isLoggedIn.value = isLoggedIn
    }

    fun updateLoadingStatus(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun updateAccountStatus(accountStatus: Int) {
        _accountStatus.intValue = accountStatus
    }
}
