package com.devndev.lamp.presentation.ui.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel

data class AssessmentUiState(
    val assessmentList: List<AssessmentListDomainModel> = emptyList(),
    val assessment: AssessmentDomainModel? = null
) {
    fun getUserSize(): Int {
        return assessment?.users?.size ?: 0
    }
}
