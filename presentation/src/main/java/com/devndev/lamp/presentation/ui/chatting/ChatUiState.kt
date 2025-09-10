package com.devndev.lamp.presentation.ui.chatting

import android.util.Log
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatItem
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.presentation.ui.common.AppointmentStatus

data class ChatUiState(
    val myInfo: MyInfoDomainModel? = null,
    val chatList: List<ChatRoomDomainModel> = emptyList(),
    val needScrollDown: Boolean = false,
    val chatInfo: ChatInfoDomainModel? = null,
    val chatMessage: List<ChatMessageDomainModel> = emptyList(),
    val chatItems: List<ChatItem> = emptyList(),
    val isLoading: Boolean = true,
    val lastFetchedMessageId: String? = null,
    val isAtBottom: Boolean = true,
    val showNewMessageBadge: Boolean = false,
    val isEmpty: Boolean = true,
    val appointment: AppointmentListDomainModel? = null,
    val assessmentList: List<AssessmentListDomainModel> = emptyList(),
    val isFirstLaunch: Boolean = true
) {
    fun getAppointmentStatus(): Int {
        if (appointment?.selectedChatAppointment != null) {
            return AppointmentStatus.CONFIRM_APPOINTMENT
        }

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
}
