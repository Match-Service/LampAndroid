package com.devndev.lamp.presentation.ui.assessment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.usecase.assessment.GetAssessmentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssessmentListViewModel @Inject constructor(
    private val getAssessmentListUseCase: GetAssessmentListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AssessmentUiState())
    val uiState: StateFlow<AssessmentUiState> = _uiState.asStateFlow()

    init {
        getAssessmentList()
    }

    private fun getAssessmentList() {
        Log.d(TAG, "getAssessmentList()")
        val tempAssessmentList = listOf(
            AssessmentListDomainModel(
                lampMatchId = 0,
                title = "이쁜2들 램프 평가하기",
                meetingTime = "2025년 7월 3일"
            ),
            AssessmentListDomainModel(
                lampMatchId = 1,
                title = "멍쟁2들 램프 평가하기",
                meetingTime = "2025년 7월 5일"
            ),
            AssessmentListDomainModel(
                lampMatchId = 2,
                title = "못난이들 램프 평가하기",
                meetingTime = "2025년 7월 8일"
            )
        )
        viewModelScope.launch {
            getAssessmentListUseCase()
                .onSuccess { assessmentList ->
                    Log.i(TAG, "assessmentList $assessmentList")
                    _uiState.update { it.copy(assessmentList = tempAssessmentList) }
                }.onFailure {
                    Log.e(TAG, "getAssessmentList Failure", it)
                }
        }
    }

    companion object {
        const val TAG = "AssessmentListViewModel"
    }
}
