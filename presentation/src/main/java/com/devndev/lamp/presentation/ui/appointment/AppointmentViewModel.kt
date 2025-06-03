package com.devndev.lamp.presentation.ui.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.chat.AppointmentItem
import com.devndev.lamp.domain.model.chat.AppointmentListDomainModel
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.RegisterAppointmentParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.chat.GetAppointmentListUseCase
import com.devndev.lamp.domain.usecase.chat.GetChatInfoUseCase
import com.devndev.lamp.domain.usecase.chat.RegisterAppointmentUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val registerAppointmentUseCase: RegisterAppointmentUseCase,
    private val getAppointmentListUseCase: GetAppointmentListUseCase,
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(AppointmentUiState())
    val uiState: StateFlow<AppointmentUiState> = _uiState.asStateFlow()

    init {
        getMyInfo()
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun updateDate(date: String) {
        _uiState.update { it.copy(date = date) }
    }

    fun registerAppointment(chatRoomId: Int) {
        viewModelScope.launch {
            val inputFormat = SimpleDateFormat("yyyy년 M월 d일 HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

            val date = inputFormat.parse(uiState.value.date)
            val meetingTimeString = outputFormat.format(date)
            Log.i(TAG, "meetingTime $meetingTimeString")
            registerAppointmentUseCase(
                RegisterAppointmentParam(
                    chatRoomId = chatRoomId,
                    location = uiState.value.location,
                    meetingTime = meetingTimeString
                )
            ).onSuccess {
                Log.d(TAG, "registerAppointment Success")
                _uiState.update { it.copy(needNavBack = true) }
            }.onFailure {
                Log.e(TAG, "registerAppointment Failure", it)
            }
        }
    }

    private fun getAppointmentList(chatRoomId: Int) {
        viewModelScope.launch {
            getAppointmentListUseCase(
                chatRoomId = chatRoomId
            ).onSuccess { appointment ->
                Log.d(TAG, "getAppointmentList Success")
                val chatInfo = uiState.value.chatInfo
                val myInfo = uiState.value.myInfo

                if (chatInfo != null && myInfo != null) {
                    val appointmentList = mapAppointmentsToItems(appointment, chatInfo, myInfo)
                    _uiState.update {
                        it.copy(
                            isEmpty = appointment.chatAppointmentList.isEmpty(),
                            appointment = appointment,
                            appointmentList = appointmentList
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isEmpty = appointment.chatAppointmentList.isEmpty(),
                            appointment = appointment
                        )
                    }
                }
            }.onFailure {
                Log.e(TAG, "getAppointmentListUseCase Failure", it)
            }
        }
    }

    fun getChatInfo(chatRoomId: Int) {
        viewModelScope.launch {
            getChatInfoUseCase(
                chatRoomId = chatRoomId
            ).onSuccess { chatInfo ->
                Log.d(TAG, "getChatInfo Success")
                _uiState.update { it.copy(chatInfo = chatInfo) }
                getAppointmentList(chatRoomId)
            }.onFailure {
                Log.e(TAG, "getChatInfo Failure", it)
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { myInfo ->
                    Log.d(TAG, "getMyInfo Success")
                    _uiState.update { it.copy(myInfo = myInfo) }
                }.onFailure {
                    Log.e(TAG, "getMyInfo Failure", it)
                }
        }
    }

    private fun mapAppointmentsToItems(
        appointmentListDomainModel: AppointmentListDomainModel,
        chatInfo: ChatInfoDomainModel,
        myInfo: MyInfoDomainModel
    ): List<AppointmentItem> {
        return appointmentListDomainModel.chatAppointmentList.map { appointment ->
            val isMine = appointment.userId == myInfo.userId

            if (isMine) {
                AppointmentItem(
                    appointment = appointment,
                    gender = myInfo.gender,
                    userName = myInfo.name,
                    isMine = true
                )
            } else {
                val userInfo = chatInfo.userInfos.find { it.userId == appointment.userId }

                AppointmentItem(
                    appointment = appointment,
                    gender = userInfo?.gender ?: "",
                    userName = userInfo?.name ?: "",
                    isMine = false
                )
            }
        }
    }

    companion object {
        const val TAG = "AppointmentViewModel"
    }
}
