package com.devndev.lamp.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val logTag = "LoginViewModel"

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    init {
        checkLoggedIn()
    }

    private fun checkLoggedIn() {
        Log.d(logTag, "checkLoggedIn()")
        viewModelScope.launch {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.d(logTag, "isLoggedIn = false")
                    _isLoggedIn.value = false
                } else {
                    Log.d(logTag, "isLoggedIn = true")
                    _isLoggedIn.value = tokenInfo != null
                }
                _isInitialized.value = true
            }
        }
    }

    fun login(callback: (Boolean) -> Unit) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                Log.e(logTag, "Login failed: ${error.message}", error)
                callback(false)
            } else if (token != null) {
                Log.d(logTag, "login success")
                _isLoggedIn.value = true
                callback(true)
            }
        }
    }

    fun logout(callback: (Boolean) -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.d(logTag, "logout fail")
                callback(false)
            } else {
                Log.d(logTag, "logout success")
                _isLoggedIn.value = false
                callback(true)
            }
        }
    }
}
