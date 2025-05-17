package com.devndev.lamp.presentation.ui.home.normal

import com.devndev.lamp.domain.model.user.MyInfoDomainModel

data class NormalHomeUiState(
    val myInfo: MyInfoDomainModel? = null,
    val isLoading: Boolean = false
)
