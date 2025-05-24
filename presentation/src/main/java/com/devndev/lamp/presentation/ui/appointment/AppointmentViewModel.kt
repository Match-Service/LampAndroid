package com.devndev.lamp.presentation.ui.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.chat.RegisterAppointmentParam
import com.devndev.lamp.domain.usecase.chat.RegisterAppointmentUseCase
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
    private val registerAppointmentUseCase: RegisterAppointmentUseCase
) : ViewModel() {
    val _uiState = MutableStateFlow(AppointmentUiState())
    val uiState: StateFlow<AppointmentUiState> = _uiState.asStateFlow()

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

    companion object {
        const val TAG = "AppointmentViewModel"
    }
}
