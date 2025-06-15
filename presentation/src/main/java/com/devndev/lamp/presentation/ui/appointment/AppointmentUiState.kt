package com.devndev.lamp.presentation.ui.appointment

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
    val isEditLoading: Boolean = false
) {
    fun getAppointmentStatus(): Int {
        if (isEmpty) {
            return AppointmentStatus.EMPTY_APPOINTMENT
        } else {
            if (appointment?.isReady == true) {
                if (appointment.canVote) {
                    if (appointment.voteReadyUserCount == appointment.allUserCount) {
                        return AppointmentStatus.CONFIRM_APPOINTMENT
                    } else {
                        for (appointment in appointment.chatAppointmentList) {
                            if (appointment.voted) {
                                return AppointmentStatus.WAITING_VOTE
                            }
                        }
                        return AppointmentStatus.BEFORE_VOTE
                    }
                } else {
                    return AppointmentStatus.WAITING_READY
                }
            } else {
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
}
