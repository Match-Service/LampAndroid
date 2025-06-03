package com.devndev.lamp.presentation.ui.home.matchinghome

import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel

data class MatchingHomeUiState(
    val myLamp: LampDomainModel? = null,
    val myInfo: MyInfoDomainModel? = null,
    val isOwner: Boolean = false,
    val isLoading: Boolean = true,
    val userStatus: String = "",
    val isMatching: Boolean = false,
    val isFullPersonnel: Boolean = false
)
