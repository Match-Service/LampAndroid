package com.devndev.lamp.presentation.ui.alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.eventbus.AlarmEventBus
import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.model.lamp.AcceptVisitParam
import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.model.lamp.RejectVisitParam
import com.devndev.lamp.domain.usecase.alarm.DeleteAlarmUseCase
import com.devndev.lamp.domain.usecase.alarm.GetAlarmUseCase
import com.devndev.lamp.domain.usecase.lamp.AcceptInviteUseCase
import com.devndev.lamp.domain.usecase.lamp.AcceptVisitUseCase
import com.devndev.lamp.domain.usecase.lamp.RejectInviteUseCase
import com.devndev.lamp.domain.usecase.lamp.RejectVisitUseCase
import com.devndev.lamp.presentation.ui.home.main.HomeViewModel.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val acceptInviteUseCase: AcceptInviteUseCase,
    private val rejectInviteUseCase: RejectInviteUseCase,
    private val acceptVisitUseCase: AcceptVisitUseCase,
    private val rejectVisitUseCase: RejectVisitUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase
) : ViewModel() {
    private val logTag = "AlarmViewModel"
    private val _uiState = MutableStateFlow(AlarmUiState())
    val uiState: StateFlow<AlarmUiState> = _uiState.asStateFlow()

    init {
        observeAlarmEvents()
        getAlarm()
    }

    private fun observeAlarmEvents() {
        viewModelScope.launch {
            AlarmEventBus.events.collect {
                _uiState.update { it.copy(alarmExist = true) }
            }
        }
    }

    fun getAlarm() {
        viewModelScope.launch {
            getAlarmUseCase()
                .onSuccess { alarms ->
                    Log.d(logTag, "getAlarm")
                    _uiState.update { it.copy(alarms = alarms) }
                    if (uiState.value.alarms.isNotEmpty()) {
                        _uiState.update { it.copy(alarmExist = true) }
                    } else {
                        _uiState.update { it.copy(alarmExist = false) }
                        Log.d(logTag, uiState.value.alarmExist.toString())
                    }
                    Log.d(logTag, "Alarms ${uiState.value.alarms}")
                }
                .onFailure { throwable ->
                    Log.e(TAG, "Failed to fetch user info", throwable)
                }
        }
    }

    fun acceptInvite(inviteRequestUserId: Int, alarmId: Int, completion: (Boolean, Int, String) -> Unit) {
        viewModelScope.launch {
            acceptInviteUseCase(
                acceptInviteParam = AcceptInviteParam(
                    inviteRequestUserId = inviteRequestUserId,
                    alarmId = alarmId
                )
            ).onSuccess { response ->
                Log.d(
                    logTag,
                    "acceptInvite, inviteRequestUserId: $inviteRequestUserId, alarmId: $alarmId"
                )
                when {
                    response.code() in 200..299 -> {
                        completion(true, 200, "")
                    }
                    response.code() == 400 -> {
                        completion(false, response.code(), "")
                    }
                    response.code() in 401..499 -> {
                        completion(false, response.code(), response.message())
                    }
                    response.code() in 500..599 -> {
                        completion(false, 500, "")
                    }
                }
            }.onFailure { throwable ->
                completion(false, 500, "")
                Log.e(logTag, "acceptInvite Exception", throwable)
            }
        }
    }

    fun rejectInvite(inviteRequestUserId: Int, alarmId: Int, completion: (Boolean, Int) -> Unit) {
        viewModelScope.launch {
            rejectInviteUseCase(
                rejectInviteParam = RejectInviteParam(
                    inviteRequestUserId = inviteRequestUserId,
                    alarmId = alarmId
                )
            ).onSuccess { response ->
                Log.d(
                    logTag,
                    "rejectInvite, inviteRequestUserId: $inviteRequestUserId, alarmId: $alarmId"
                )
                when {
                    response.code() in 200..299 -> {
                        completion(true, 200)
                    }
                    response.code() in 400..499 -> {
                        completion(false, 400)
                    }
                    response.code() in 500..599 -> {
                        completion(false, 500)
                    }
                }
            }.onFailure { throwable ->
                completion(false, 500)
                Log.e(logTag, "rejectInvite Exception", throwable)
            }
        }
    }

    fun acceptVisit(
        lampId: Int,
        visitUserId: Int,
        alarmId: Int,
        completion: (Boolean, Int, String) -> Unit
    ) {
        viewModelScope.launch {
            acceptVisitUseCase(
                AcceptVisitParam(visitUserId, alarmId)
            ).onSuccess { response ->
                Log.d(
                    logTag,
                    "acceptVisit, lampId: $lampId visitUserId: $visitUserId alarmId: $alarmId"
                )
                // TODO 예외 케이스 정리 되면 수정 필요
                when {
                    response.code() in 200..299 -> {
                        completion(true, 200, "")
                    }
                    response.code() == 400 -> {
                        completion(false, response.code(), "")
                    }
                    response.code() in 401..499 -> {
                        completion(false, response.code(), response.message())
                    }
                    response.code() in 500..599 -> {
                        completion(false, 500, "")
                    }
                }
            }.onFailure { throwable ->
                completion(false, 500, "")
                Log.e(logTag, "acceptVisit Exception", throwable)
            }
        }
    }

    fun rejectVisit(
        lampId: Int,
        visitUserId: Int,
        alarmId: Int,
        completion: (Boolean, Int) -> Unit
    ) {
        viewModelScope.launch {
            rejectVisitUseCase(
                RejectVisitParam(visitUserId, alarmId)
            ).onSuccess { response ->
                Log.d(
                    logTag,
                    "rejectVisit, lampId: $lampId visitUserId: $visitUserId alarmId: $alarmId"
                )
                when {
                    response.code() in 200..299 -> {
                        completion(true, 200)
                    }
                    response.code() in 400..499 -> {
                        completion(false, 400)
                    }
                    response.code() in 500..599 -> {
                        completion(false, 500)
                    }
                }
            }.onFailure { throwable ->
                completion(false, 0)
                Log.e(logTag, "rejectVisit Exception", throwable)
            }
        }
    }

    fun deleteAlarm(alarmId: Int) {
        viewModelScope.launch {
            deleteAlarmUseCase(alarmId)
                .onSuccess {
                    Log.d(logTag, "deleteAlarm")
                    getAlarm()
                }.onFailure {
                    Log.e(logTag, "deleteAlarm Exception", it)
                }
        }
    }
}
