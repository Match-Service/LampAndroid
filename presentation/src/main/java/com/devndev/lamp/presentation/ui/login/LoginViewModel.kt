package com.devndev.lamp.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.GoogleTokenParam
import com.devndev.lamp.domain.usecase.GoogleAuthUseCase
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleSignInClient: GoogleSignInClient,
    private val googleAuthUseCase: GoogleAuthUseCase
) : ViewModel() {
    private val logTag = "LoginViewModel"

    fun getSignInIntent(): Intent {
        Log.d(logTag, "getSignInIntent()")
        return googleSignInClient.signInIntent
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            AuthManager.updateLoadingStatus(true)
            val account = GoogleSignIn.getLastSignedInAccount(context)

            AuthManager.updateLoginStatus(account != null)
            AuthManager.updateLoadingStatus(false)
        }
        Log.d(logTag, "checkLoginStatus() status: ${AuthManager.isLoggedIn.value}")
    }

    fun signInWithGoogle(intentData: Intent?) {
        Log.d(logTag, "signInWithGoogle")
        val task = GoogleSignIn.getSignedInAccountFromIntent(intentData)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            authenticateWithGoogle(idToken.toString())
            //    AuthManager.updateLoginStatus(account != null)
            //    Log.d(logTag, "signInWithGoogle() isLoggedIn ${AuthManager.isLoggedIn.value}")
        } catch (e: ApiException) {
            AuthManager.updateLoginStatus(false)
            Log.e(logTag, "signInResult:failed", e)
        }
    }

    private fun authenticateWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val googleTokenParam = GoogleTokenParam(idToken = idToken)
                val tokenResult = googleAuthUseCase(googleTokenParam)
                Log.d(logTag, "Google Token Result: ${tokenResult.token}")
                Log.d(logTag, "Signup Token: ${tokenResult.signupToken}")
                if (tokenResult.token == null && tokenResult.signupToken != null) {
                    AuthManager.updateAccountStatus(AccountStatus.NEW_ACCOUNT)
                } else if (tokenResult.token != null && tokenResult.signupToken == null) {
                    AuthManager.updateAccountStatus(AccountStatus.SIGNED_IN_ACCOUNT)
                }
            } catch (e: Exception) {
                Log.d(logTag, e.message.toString())
            }
        }
    }

    fun signOut() {
        Log.d(logTag, "signOut()")
        googleSignInClient.signOut().addOnCompleteListener {
            AuthManager.updateLoginStatus(false)
            Log.d(logTag, "signOut() isLoggedIn ${AuthManager.isLoggedIn.value}")
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
