package com.devndev.lamp.presentation.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.login.GoogleTokenParam
import com.devndev.lamp.domain.usecase.login.CheckIsNeedSignOutUseCase
import com.devndev.lamp.domain.usecase.login.GoogleAuthUseCase
import com.devndev.lamp.domain.usecase.login.SaveIsNeedSignOutUseCase
import com.devndev.lamp.domain.usecase.login.SetTokenUseCase
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.devndev.lamp.presentation.utils.IconStatusManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleSignInClient: GoogleSignInClient,
    private val googleAuthUseCase: GoogleAuthUseCase,
    private val checkIsNeedSignOutUseCase: CheckIsNeedSignOutUseCase,
    private val saveIsNeedSignOutUseCase: SaveIsNeedSignOutUseCase,
    private val setTokenUseCase: SetTokenUseCase
) : ViewModel() {
    private val logTag = "LoginViewModel"
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        IconStatusManager.setIconStatus("NONE")
        checkLoginStatus()
    }

    fun getSignInIntent(): Intent {
        Log.d(logTag, "getSignInIntent()")
        return googleSignInClient.signInIntent
    }

    private fun checkLoginStatus() {
        if (checkIsNeedSignOutUseCase()) {
            signOut()
            saveIsNeedSignOutUseCase(false)
        }
    }

    fun signInWithGoogle(intentData: Intent?) {
        Log.d(logTag, "signInWithGoogle")
        val task = GoogleSignIn.getSignedInAccountFromIntent(intentData)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken
            Log.d(logTag, "idToken $idToken")
            authenticateWithGoogle(idToken.toString())
        } catch (e: ApiException) {
            AuthManager.updateAccountStatus(AccountStatus.NONE)
            Log.e(logTag, "signInResult:failed", e)
        }
    }

    fun signInSuccess() {
        viewModelScope.launch {
            Log.d(logTag, "signInSuccess()")
            _uiState.update { state ->
                state.copy(
                    isUserLoggedIn = true
                )
            }
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
                    AuthManager.signUpToken = tokenResult.signupToken.toString()
                } else if (tokenResult.token != null && tokenResult.signupToken == null) {
//                    AuthManager.updateAccountStatus(AccountStatus.SIGNED_IN_ACCOUNT)
                    setTokenUseCase(tokenResult.token.toString())
                    _uiState.update { state ->
                        state.copy(isUserLoggedIn = true)
                    }
                }
            } catch (e: Exception) {
                Log.e(logTag, "authenticateWithGoogle", e)
            }
        }
    }

    fun signOut() {
        Log.d(logTag, "signOut()")
        googleSignInClient.signOut().addOnCompleteListener {
            AuthManager.updateAccountStatus(AccountStatus.NONE)
            Log.d(logTag, "signOut() Completed")
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
