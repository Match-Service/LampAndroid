package com.devndev.lamp.presentation.ui.home.normal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.usecase.assessment.GetAssessmentListUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.presentation.ui.home.normal.NormalHomeUiState
import com.devndev.lamp.presentation.utils.IconStatusManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NormalHomeViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getAssessmentListUseCase: GetAssessmentListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(NormalHomeUiState())
    val uiState: StateFlow<NormalHomeUiState> = _uiState.asStateFlow()

    init {
        getMyInfo()
        getAssessmentList()
    }

    private fun getMyInfo() {
        updateLoadingState(true)
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { userInfo ->
                    Log.d(TAG, "getMyInfo")
                    _uiState.update { it.copy(myInfo = userInfo) }
                    Log.d(TAG, "My Info ${_uiState.value.myInfo}")
                    _uiState.value.myInfo?.gender.let {
                        if (it != null) {
                            IconStatusManager.setIconStatus(it)
                        }
                    }
                    updateLoadingState(false)
                }
                .onFailure { throwable ->
                    Log.e(TAG, "Failed to fetch user info", throwable)
                }
        }
    }

    private fun getAssessmentList() {
        Log.d(TAG, "getAssessmentList()")
        viewModelScope.launch {
            getAssessmentListUseCase()
                .onSuccess { assessmentList ->
                    Log.i(TAG, "assessmentList $assessmentList")
                    _uiState.update { it.copy(assessmentList = assessmentList) }
                }.onFailure {
                    Log.e(TAG, "getAssessmentList Failure", it)
                }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    companion object {
        const val TAG = "NormalHomeViewModel"
    }
}
