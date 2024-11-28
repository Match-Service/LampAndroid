package com.devndev.lamp.presentation.ui.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.model.ValidateNameParam
import com.devndev.lamp.domain.usecase.CheckIsNeedSignOutUseCase
import com.devndev.lamp.domain.usecase.SaveIsNeedSignOutUseCase
import com.devndev.lamp.domain.usecase.ValidateInstagramUseCase
import com.devndev.lamp.domain.usecase.ValidateNameUseCase
import com.devndev.lamp.presentation.ui.common.InstagramStep
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateInstagramUseCase: ValidateInstagramUseCase,
    private val checkIsNeedSignOutUseCase: CheckIsNeedSignOutUseCase,
    private val saveIsNeedSignOutUseCase: SaveIsNeedSignOutUseCase
) : ViewModel() {
    private val logTag = "RegistrationViewModel"

    private val _currentStep = MutableStateFlow(1)
    val currentStep: StateFlow<Int> = _currentStep

    private val _isDuplicateName = MutableStateFlow(false)
    val isDuplicateName: StateFlow<Boolean> = _isDuplicateName

    private val _instagramStep = MutableStateFlow(InstagramStep.NONE)
    val instagramStep: StateFlow<Int> = _instagramStep

    fun updateCurrentStep(step: Int) {
        _currentStep.value = step
        Log.d(logTag, "updateCurrentStep: ${currentStep.value}")
    }

    fun updateIsDuplicateName(isDuplicate: Boolean) {
        _isDuplicateName.value = isDuplicate
        Log.d(logTag, "updateIsDuplicateName: ${isDuplicateName.value}")
    }

    fun updateInstagramStep(step: Int) {
        _instagramStep.value = step
        Log.d(logTag, "updateInstagramStep: ${instagramStep.value}")
    }

    fun checkIsDuplicateName(name: String) {
        viewModelScope.launch {
            try {
                if (validateNameUseCase(ValidateNameParam(name))) {
                    Log.d(logTag, "checkIsDuplicateName: false")
                    _isDuplicateName.value = false
                    _currentStep.value = currentStep.value + 1
                } else {
                    Log.d(logTag, "checkIsDuplicateName: true")
                    _isDuplicateName.value = true
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsDuplicateName", e)
            }
        }
    }

    fun checkIsValidInstagramId(instagramId: String) {
        viewModelScope.launch {
            try {
                if (validateInstagramUseCase(ValidateInstagramParam(instagramId))) {
                    Log.d(logTag, "checkIsValidInstagramId: true")
                    _instagramStep.value = InstagramStep.VALID
                } else {
                    Log.d(logTag, "checkIsValidInstagramId: false")
                    _instagramStep.value = InstagramStep.INVALID
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsValidInstagramId", e)
            }
        }
    }

    fun checkIsNeedSignOut(): Boolean {
        return checkIsNeedSignOutUseCase()
    }

    fun saveIsNeedSignOut(isNeedSignOut: Boolean) {
        saveIsNeedSignOutUseCase(isNeedSignOut)
    }
}
