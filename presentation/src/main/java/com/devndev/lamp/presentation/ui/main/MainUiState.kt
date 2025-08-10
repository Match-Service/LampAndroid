package com.devndev.lamp.presentation.ui.main

data class MainUiState(
    val isFirstOpen: Boolean? = null,
    val isAssessmentListExist: Boolean = false,
    val userStatus: String = "",
    val isLoading: Boolean = true
)
