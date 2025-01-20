package com.devndev.lamp.presentation.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.lamp.InviteUsersUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.SearchUserUseCase
import com.devndev.lamp.presentation.ui.common.InviteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val inviteUsersUseCase: InviteUsersUseCase,
    private val getMyLampUseCase: GetMyLampUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    private val logTag = "SearchViewModel"
    private val _users = mutableStateListOf<UserDomainModel>()
    val users: List<UserDomainModel> = _users

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _myLamp = MutableStateFlow<LampDomainModel?>(null)
    val myLamp: StateFlow<LampDomainModel?> = _myLamp

    private val _inviteStatus = MutableStateFlow(InviteStatus.NONE)
    val inviteStatus = _inviteStatus

    init {
        getMyInfo()
        getLampData()
    }

    fun updateInviteStatus(status: Int) {
        _inviteStatus.value = status
        Log.d(logTag, "updateInviteStatus: ${inviteStatus.value}")
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "fetchData")
                _myInfo.value = getMyInfoUseCase()
            } catch (e: HttpException) {
                Log.e(logTag, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }

    private fun getLampData() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "getLampData")
                _myLamp.value = getMyLampUseCase()
                Log.d(logTag, "My Lamp ${myLamp.value}")
            } catch (e: HttpException) {
                Log.e(logTag, "getLampData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "getLampData Exception", e)
            }
        }
    }

    fun searchUsers(name: String) {
        viewModelScope.launch {
            _users.clear()
            _users.addAll(searchUserUseCase(name))
            if (users.isEmpty()) {
                updateInviteStatus(InviteStatus.USER_NOT_FOUNT)
            } else {
                updateInviteStatus(InviteStatus.SEARCHED)
            }
            Log.d(logTag, users.toString())
        }
    }

    fun resetUsers() {
        _users.clear()
    }

    fun inviteUsers(users: List<String>) {
        viewModelScope.launch {
            try {
                val inviteUsersParam = InviteUsersParam(users)
                Log.d(logTag, "inviteUsers(), users $users")
                myLamp.value?.lamp?.lampId?.let { inviteUsersUseCase(it, inviteUsersParam) }
            } catch (e: HttpException) {
                Log.e(logTag, "inviteUsers HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "inviteUsers Exception", e)
            }
        }
    }
}
