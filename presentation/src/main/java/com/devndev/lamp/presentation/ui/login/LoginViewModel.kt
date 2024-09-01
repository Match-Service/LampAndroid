package com.devndev.lamp.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.presentation.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
LoginViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val logTag = "LoginViewModel"

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(): Intent {
        Log.d(logTag, "getSignInIntent()")
        return googleSignInClient.signInIntent
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            _isLoading.value = true
            val account = GoogleSignIn.getLastSignedInAccount(context)
            _isLoggedIn.value = account != null
            _isLoading.value = false
        }
        Log.d(logTag, "checkLoginStatus() status: ${_isLoggedIn.value}")
    }

    fun signInWithGoogle(intentData: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intentData)
        try {
            val account = task.getResult(ApiException::class.java)
            _isLoggedIn.value = account != null
            Log.d(logTag, "signInWithGoogle()")
        } catch (e: ApiException) {
            _isLoggedIn.value = false
            Log.e(logTag, "signInResult:failed", e)
        }
    }

    fun signOut() {
        Log.d(logTag, "signOut()")
        googleSignInClient.signOut().addOnCompleteListener {
            _isLoggedIn.value = false
        }
    }

    /*
        카카오 로그인
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
     */
}
