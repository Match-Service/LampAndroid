package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.notification.FcmNotificationParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.login.SignOutUseCase
import com.devndev.lamp.domain.usecase.notification.SendFcmNotificationUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val sendFcmNotificationUseCase: SendFcmNotificationUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val logTag = "MyPageViewModel"

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    // 테스트용 fcm 코드 추후 삭제
    private lateinit var fcmToken: String

    init {
        fetchData()
        getFcmToken()
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            viewModelScope.launch {
                val token = task.result
                fcmToken = token
                Log.d(logTag, "getFcmToken() $fcmToken")
            }
        }
    }

    fun sendFcmNotification(title: String, message: String) {
        viewModelScope.launch {
            Log.d(logTag, "token $fcmToken, title $title, message $message")
            sendFcmNotificationUseCase(
                FcmNotificationParam(
                    pushToken = fcmToken,
                    title = title,
                    message = message
                )
            )
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "fetchData")
                _myInfo.value = getMyInfoUseCase()
                Log.d(logTag, "My Info ${myInfo.value}")
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }

    fun signOut() {
        Log.d(logTag, "signOut()")
        viewModelScope.launch {
            googleSignInClient.signOut().addOnCompleteListener {
                AuthManager.updateAccountStatus(AccountStatus.NONE)
                Log.d(logTag, "signOut() completed")
            }
            signOutUseCase()
            _uiState.update { state ->
                state.copy(
                    isLoggedOut = true
                )
            }
        }
    }
}
