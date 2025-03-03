package com.devndev.lamp.presentation.ui.alarm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.usecase.alarm.GetAlarmUseCase
import com.devndev.lamp.domain.usecase.lamp.AcceptInviteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAlarmUseCase: GetAlarmUseCase,
    private val acceptInviteUseCase: AcceptInviteUseCase
) : ViewModel() {
    private val logTag = "AlarmViewModel"

    private val _alarms = MutableStateFlow<List<AlarmDomainModel>>(emptyList())
    val alarms: StateFlow<List<AlarmDomainModel>> = _alarms

    init {
        getAlarm()
    }

    private fun getAlarm() {
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

    fun acceptInvite(lampId: Int, inviteUserId: Int, alarmId: Int) {
        viewModelScope.launch {
            try {
                Log.d(
                    logTag,
                    "acceptInvite, lampId: $lampId, inviteUserId: $inviteUserId, alarmId: $alarmId"
                )
                acceptInviteUseCase(
                    lampId = lampId,
                    acceptInviteParam = AcceptInviteParam(inviteUserId = inviteUserId, alarmId = alarmId)
                )
            } catch (e: Exception) {
                Log.e(logTag, "acceptInvite Exception", e)
            }
        }
    }
}
