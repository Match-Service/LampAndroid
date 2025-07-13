package com.devndev.lamp.presentation.ui.assessment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.assessment.AssessmentParam
import com.devndev.lamp.domain.usecase.assessment.AssessmentUseCase
import com.devndev.lamp.domain.usecase.assessment.GetAssessmentListUseCase
import com.devndev.lamp.domain.usecase.assessment.GetAssessmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentListViewModel @Inject constructor(
    private val getAssessmentListUseCase: GetAssessmentListUseCase,
    private val getAssessmentUseCase: GetAssessmentUseCase,
    private val assessmentUseCase: AssessmentUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AssessmentUiState())
    val uiState: StateFlow<AssessmentUiState> = _uiState.asStateFlow()

    init {
        getAssessmentList()
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

    fun getAssessment(lampMatchId: Int) {
        Log.d(TAG, "getAssessment $lampMatchId")
        viewModelScope.launch {
            getAssessmentUseCase(lampMatchId)
                .onSuccess { assessment ->
                    Log.i(TAG, "assessment $assessment")
                    _uiState.update { it.copy(assessment = assessment) }
                }.onFailure {
                    Log.e(TAG, "getAssessment Failure", it)
                }
        }
    }

    fun assessment(assessmentParam: AssessmentParam) {
        Log.d(TAG, "assessment $assessmentParam")
        viewModelScope.launch {
            assessmentUseCase(assessmentParam)
        }
    }

    companion object {
        const val TAG = "AssessmentListViewModel"
    }
}
