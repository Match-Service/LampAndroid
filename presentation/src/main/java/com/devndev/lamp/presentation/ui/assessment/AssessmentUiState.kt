package com.devndev.lamp.presentation.ui.assessment

import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel

data class AssessmentUiState(
    val assessmentList: List<AssessmentListDomainModel> = emptyList()
)
