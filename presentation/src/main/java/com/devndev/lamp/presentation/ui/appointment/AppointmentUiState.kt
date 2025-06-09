package com.devndev.lamp.presentation.ui.appointment

import com.devndev.lamp.domain.model.chat.AppointmentDomainModel
import com.devndev.lamp.domain.model.chat.AppointmentItem
import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel

data class AppointmentUiState(
    val isEmpty: Boolean = true,
    val location: String = "",
    val date: String = "",
    val needNavBack: Boolean = false,
    val appointment: AppointmentListDomainModel? = null,
    val chatInfo: ChatInfoDomainModel? = null,
    val myInfo: MyInfoDomainModel? = null,
    val appointmentList: List<AppointmentItem> = emptyList(),
    val editAppointment: AppointmentDomainModel? = null,
    val isEditLoading: Boolean = false
)
