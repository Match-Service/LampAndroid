package com.devndev.lamp.presentation.ui.main

import android.util.Log
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel

data class MainUiState(
    val isFirstOpen: Boolean? = null,
    val isNormalHomeScreen: Boolean = false,
    val assessmentList: List<AssessmentListDomainModel> = emptyList()
) {
    fun getIsNeedAssessTopBar(): Boolean {
        val isNeedAssessTopBar = isNormalHomeScreen && assessmentList.isNotEmpty()
        Log.d("MainUiState", "isNeedAssessTopBar $isNeedAssessTopBar")
        return isNeedAssessTopBar
    }
}
