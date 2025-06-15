package com.devndev.lamp.domain.model.chat

data class AppointmentListDomainModel(
    val canVote: Boolean,
    val isReady: Boolean,
    val voteReadyUserCount: Int,
    val allUserCount: Int,
    val chatAppointmentList: List<AppointmentDomainModel>
)

data class AppointmentDomainModel(
    val chatAppointmentId: Int,
    val createdAt: String,
    val location: String,
    val meetingTime: String,
    val agreeCount: Int,
    val createdUserId: Int,
    val voted: Boolean
)

data class AppointmentItem(
    val appointment: AppointmentDomainModel,
    val gender: String,
    val userName: String,
    val isMine: Boolean
)
