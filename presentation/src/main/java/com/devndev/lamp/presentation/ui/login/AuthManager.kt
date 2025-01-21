package com.devndev.lamp.presentation.ui.login

import androidx.compose.runtime.mutableIntStateOf
import com.devndev.lamp.presentation.ui.common.AccountStatus

object AuthManager {
    private val _accountStatus = mutableIntStateOf(AccountStatus.NONE)
    val accountStatus = _accountStatus

    var signUpToken = ""

    fun updateAccountStatus(accountStatus: Int) {
        _accountStatus.intValue = accountStatus
    }
}
