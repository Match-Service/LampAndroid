package com.devndev.lamp.presentation.ui.appointment

import android.util.Log
import com.devndev.lamp.domain.model.chat.AppointmentDomainModel
import com.devndev.lamp.domain.model.chat.AppointmentItem
import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.presentation.ui.common.AppointmentStatus

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
    val isLoading: Boolean = true
) {
    fun getAppointmentStatus(): Int {
        if (isEmpty) {
            Log.d("uiState", "Empty_appointment")
            return AppointmentStatus.EMPTY_APPOINTMENT
        } else {
            if (appointment?.isReady == true) {
                if (appointment.canVote) {
                    for (appointment in appointment.chatAppointmentList) {
                        if (appointment.voted) {
                            Log.d("uiState", "waiting vote")
                            return AppointmentStatus.WAITING_VOTE
                        }
                    }
                    Log.d("uiState", "before vote")
                    return AppointmentStatus.BEFORE_VOTE
                } else {
                    Log.d("uiState", "waiting_ready")
                    return AppointmentStatus.WAITING_READY
                }
            } else {
                Log.d("uiState", "before ready")
                return AppointmentStatus.BEFORE_READY
            }
        }
    }

    fun getVoteCount(): Int {
        var count = 0
        for (a in appointment?.chatAppointmentList ?: emptyList()) {
            count += a.agreeCount
        }
        return count
    }

    fun getVotedAppointment(): AppointmentItem? {
        for (appointment in appointmentList) {
            if (appointment.appointment.voted) {
                return appointment
            }
        }
        return null
    }
}
