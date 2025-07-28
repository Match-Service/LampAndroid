package com.devndev.lamp.presentation.ui.mypage

import com.devndev.lamp.domain.model.setting.PushSettingDomainModel

data class MyPageUiState(
    val isLoggedOut: Boolean = false,
    val pushSetting: PushSettingDomainModel? = null
)
