package com.devndev.lamp.presentation.ui.alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.model.lamp.AcceptVisitParam
import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.model.lamp.RejectVisitParam
import com.devndev.lamp.domain.usecase.alarm.GetAlarmUseCase
import com.devndev.lamp.domain.usecase.lamp.AcceptInviteUseCase
import com.devndev.lamp.domain.usecase.lamp.AcceptVisitUseCase
import com.devndev.lamp.domain.usecase.lamp.RejectInviteUseCase
import com.devndev.lamp.domain.usecase.lamp.RejectVisitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val acceptInviteUseCase: AcceptInviteUseCase,
    private val rejectInviteUseCase: RejectInviteUseCase,
    private val acceptVisitUseCase: AcceptVisitUseCase,
    private val rejectVisitUseCase: RejectVisitUseCase
) : ViewModel() {
    private val logTag = "AlarmViewModel"

    private val _alarms = MutableStateFlow<List<AlarmDomainModel>>(emptyList())
    val alarms: StateFlow<List<AlarmDomainModel>> = _alarms

    init {
        getAlarm()
    }

    fun getAlarm() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "getAlarm")
                _alarms.value = getAlarmUseCase()
                Log.d(logTag, "Alarms ${alarms.value}")
            } catch (e: Exception) {
                Log.e(logTag, "getAlarm Exception", e)
            }
        }
    }

    fun acceptInvite(inviteRequestUserId: Int, alarmId: Int) {
        viewModelScope.launch {
            try {
                Log.d(
                    logTag,
                    "acceptInvite, inviteRequestUserId: $inviteRequestUserId, alarmId: $alarmId"
                )
                acceptInviteUseCase(
                    acceptInviteParam = AcceptInviteParam(
                        inviteRequestUserId = inviteRequestUserId,
                        alarmId = alarmId
                    )
                )
                getAlarm()
            } catch (e: Exception) {
                Log.e(logTag, "acceptInvite Exception", e)
            }
        }
    }

    fun rejectInvite(inviteRequestUserId: Int, alarmId: Int) {
        viewModelScope.launch {
            try {
                Log.d(
                    logTag,
                    "rejectInvite, inviteRequestUserId: $inviteRequestUserId, alarmId: $alarmId"
                )
                rejectInviteUseCase(
                    rejectInviteParam = RejectInviteParam(
                        inviteRequestUserId = inviteRequestUserId,
                        alarmId = alarmId
                    )
                )
                getAlarm()
            } catch (e: Exception) {
                Log.e(logTag, "rejectInvite Exception", e)
            }
        }
    }

    fun acceptVisit(lampId: Int, visitUserId: Int, alarmId: Int) {
        viewModelScope.launch {
            try {
                Log.d(
                    logTag,
                    "acceptVisit, lampId: $lampId visitUserId: $visitUserId alarmId: $alarmId"
                )
                acceptVisitUseCase(
                    AcceptVisitParam(visitUserId, alarmId)
                )
                getAlarm()
            } catch (e: Exception) {
                Log.e(logTag, "acceptVisit Exception", e)
            }
        }
    }

    fun rejectVisit(lampId: Int, visitUserId: Int, alarmId: Int) {
        viewModelScope.launch {
            try {
                Log.d(
                    logTag,
                    "rejectVisit, lampId: $lampId visitUserId: $visitUserId alarmId: $alarmId"
                )
                rejectVisitUseCase(
                    RejectVisitParam(visitUserId, alarmId)
                )
                getAlarm()
            } catch (e: Exception) {
                Log.e(logTag, "rejectVisit Exception", e)
            }
        }
    }
}
