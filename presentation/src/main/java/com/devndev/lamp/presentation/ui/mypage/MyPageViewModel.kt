package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.setting.PushSettingDomainModel
import com.devndev.lamp.domain.model.setting.PushSettingParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.login.SignOutUseCase
import com.devndev.lamp.domain.usecase.setting.GetPushSettingUseCase
import com.devndev.lamp.domain.usecase.setting.PutPushSettingUseCase
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
    private val signOutUseCase: SignOutUseCase,
    private val getPushSettingUseCase: GetPushSettingUseCase,
    private val putPushSettingUseCase: PutPushSettingUseCase
) : ViewModel() {
    private val logTag = "MyPageViewModel"

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    // 테스트용 fcm 코드 추후 삭제
    private lateinit var fcmToken: String

    init {
        getMyInfo()
        getFcmToken()
        getPushSetting()
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

    private fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { userInfo ->
                    Log.d(logTag, "getMyInfo")
                    _myInfo.value = userInfo
                    Log.d(logTag, "My Info ${myInfo.value}")
                }
                .onFailure { throwable ->
                    Log.e(logTag, "Failed to fetch user info", throwable)
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

    private fun getPushSetting() {
        viewModelScope.launch {
            getPushSettingUseCase()
                .onSuccess { pushSetting ->
                    _uiState.update { it.copy(pushSetting = pushSetting) }
                }.onFailure {
                    Log.e(logTag, " getPushSetting Failure", it)
                }
        }
    }

    fun updatePushSetting(pushSettingDomainModel: PushSettingDomainModel) {
        viewModelScope.launch {
            Log.d(logTag, "updatePushSetting $pushSettingDomainModel")
            val pushSettingParam = PushSettingParam(
                allPush = pushSettingDomainModel.allPush,
                lampInvite = pushSettingDomainModel.lampInvite,
                lampVisit = pushSettingDomainModel.lampVisit,
                newMatch = pushSettingDomainModel.newMatch,
                receiveMessage = pushSettingDomainModel.receiveMessage,
                receiveAssessment = pushSettingDomainModel.receiveAssessment
            )
            putPushSettingUseCase(pushSettingParam)
                .onSuccess {
                    Log.d(logTag, "upDatePushSetting Success")
                    getPushSetting()
                }.onFailure {
                    Log.d(logTag, "upDatePushSetting Failure", it)
                }
        }
    }

    fun rejectPushSetting() {
        viewModelScope.launch {
            val pushSettingParam = PushSettingParam(
                allPush = false,
                lampInvite = false,
                lampVisit = false,
                newMatch = false,
                receiveAssessment = false,
                receiveMessage = false
            )
            putPushSettingUseCase(pushSettingParam)
        }
    }
}
