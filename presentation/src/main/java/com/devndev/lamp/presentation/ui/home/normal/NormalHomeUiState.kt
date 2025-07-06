package com.devndev.lamp.presentation.ui.home.normal

import android.util.Log
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel

data class NormalHomeUiState(
    val myInfo: MyInfoDomainModel? = null,
    val isLoading: Boolean = false,
    val assessmentList: List<AssessmentListDomainModel> = emptyList()
) {
    fun getIsNeedAssessTopBar(): Boolean {
        val isNeedAssessTopBar = assessmentList.isNotEmpty()
        Log.d("NormalHomeUiState", "isNeedAssessTopBar $isNeedAssessTopBar")
        return isNeedAssessTopBar
    }
}
