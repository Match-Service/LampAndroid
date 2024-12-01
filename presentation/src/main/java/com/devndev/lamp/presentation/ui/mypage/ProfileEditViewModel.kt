package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.usecase.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.ValidateInstagramUseCase
import com.devndev.lamp.presentation.ui.common.InstagramAuth
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val validateInstagramUseCase: ValidateInstagramUseCase
) : ViewModel() {
    private val logTag = "ProfileEditViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _instagramStep = MutableStateFlow(InstagramAuth.BEFORE_AUTH)
    val instagramStep: StateFlow<Int> = _instagramStep

    fun updateInstagramStep(step: Int) {
        _instagramStep.value = step
        Log.d(logTag, "updateInstagramStep: ${instagramStep.value}")
    }

    init {
        fetchData()
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
