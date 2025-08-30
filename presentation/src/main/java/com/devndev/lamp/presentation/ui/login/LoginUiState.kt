package com.devndev.lamp.presentation.ui.login

data class LoginUiState(
    val isFirstOpen: Boolean? = null,
    val isLoading: Boolean = false,
    val isUserLoggedIn: Boolean = false
)
