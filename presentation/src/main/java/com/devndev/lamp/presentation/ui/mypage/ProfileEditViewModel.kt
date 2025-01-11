package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.data.dto.request.ModifyUser
import com.devndev.lamp.domain.model.ModifyUserParam
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.usecase.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.ModifyUserUseCase
import com.devndev.lamp.domain.usecase.ValidateInstagramUseCase
import com.devndev.lamp.presentation.ui.common.InstagramAuth
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val validateInstagramUseCase: ValidateInstagramUseCase,
    private val modifyUserUseCase: ModifyUserUseCase
) : ViewModel() {
    private val logTag = "ProfileEditViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _instagramStep = MutableStateFlow(InstagramAuth.BEFORE_AUTH)
    val instagramStep: StateFlow<Int> = _instagramStep

    // 테스트용 fcm 코드 추후 삭제
    private lateinit var fcmToken: String

    fun updateInstagramStep(step: Int) {
        _instagramStep.value = step
        Log.d(logTag, "updateInstagramStep: ${instagramStep.value}")
    }

    init {
        fetchData()
        getFcmToken()
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

    fun modifyUser(modifyUser: ModifyUser) {
        viewModelScope.launch {
            Log.d(logTag, "modifyUserRequest $modifyUser pushToken $fcmToken")
            try {
                val response = modifyUserUseCase(
                    ModifyUserParam(
                        name = modifyUser.name,
                        job = modifyUser.job,
                        jobName = modifyUser.jobName,
                        gender = modifyUser.gender,
                        birth = modifyUser.birth,
                        instagramId = modifyUser.instagramId,
                        bio = modifyUser.bio,
                        profileImages = modifyUser.profileImages,
                        alarmSetting = modifyUser.alarmSetting,
                        bioQuestions = modifyUser.bioQuestions,
                        pushToken = fcmToken
                    )
                )

                if (response) {
                    Log.d(logTag, "modifyUser: true")
                } else {
                    Log.d(logTag, "modifyUser: fail")
                }
            } catch (e: ApiException) {
                Log.e(logTag, "modifyUser", e)
            }
        }
    }

    fun checkIsValidInstagramId(instagramId: String) {
        viewModelScope.launch {
            try {
                if (validateInstagramUseCase(ValidateInstagramParam(instagramId))) {
                    Log.d(logTag, "checkIsValidInstagramId: true")
                    _instagramStep.value = InstagramAuth.AUTH_SUCCESS
                } else {
                    Log.d(logTag, "checkIsValidInstagramId: false")
                    _instagramStep.value = InstagramAuth.AUTH_FAIL
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsValidInstagramId", e)
            }
        }
    }
}
