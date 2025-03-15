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
import com.devndev.lamp.domain.usecase.lamp.RequestVisitUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.SearchInviteUserUseCase
import com.devndev.lamp.domain.usecase.user.SearchVisitUserUseCase
import com.devndev.lamp.presentation.ui.common.SearchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchInviteUserUseCase: SearchInviteUserUseCase,
    private val searchVisitUserUseCase: SearchVisitUserUseCase,
    private val inviteUsersUseCase: InviteUsersUseCase,
    private val getMyLampUseCase: GetMyLampUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val requestVisitUseCase: RequestVisitUseCase
) : ViewModel() {
    private val logTag = "SearchViewModel"
    private val _users = mutableStateListOf<UserDomainModel>()
    val users: List<UserDomainModel> = _users

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _myLamp = MutableStateFlow<LampDomainModel?>(null)
    val myLamp: StateFlow<LampDomainModel?> = _myLamp

    private val _searchStatus = MutableStateFlow(SearchStatus.NONE)
    val searchStatus = _searchStatus

    init {
        getMyInfo()
        getLampData()
    }

    fun updateSearchStatus(status: Int) {
        _searchStatus.value = status
        Log.d(logTag, "updateSearchStatus: ${searchStatus.value}")
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

    fun searchInviteUsers(name: String) {
        viewModelScope.launch {
            _users.clear()
            _users.addAll(searchInviteUserUseCase(name))
            if (users.isEmpty()) {
                updateSearchStatus(SearchStatus.USER_NOT_FOUNT)
            } else {
                updateSearchStatus(SearchStatus.SEARCHED)
            }
            Log.d(logTag, users.toString())
        }
    }

    fun searchVisitUsers(name: String) {
        viewModelScope.launch {
            _users.clear()
            _users.addAll(searchVisitUserUseCase(name))
            if (users.isEmpty()) {
                updateSearchStatus(SearchStatus.USER_NOT_FOUNT)
            } else {
                updateSearchStatus(SearchStatus.SEARCHED)
            }
            Log.d(logTag, users.toString())
        }
    }

    fun resetUsers() {
        _users.clear()
    }

    fun inviteUsers(users: List<Int>) {
        viewModelScope.launch {
            try {
                val inviteUsersParam = InviteUsersParam(users)
                Log.d(logTag, "inviteUsers(), users $users")
                myLamp.value?.lamp?.lampId?.let {
                    Log.d(logTag, "inviteUsers(), myLampId $it")
                    inviteUsersUseCase(it, inviteUsersParam)
                }
            } catch (e: HttpException) {
                Log.e(logTag, "inviteUsers HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "inviteUsers Exception", e)
            }
        }
    }

    fun requestVisit(lampId: Int) {
        viewModelScope.launch {
            try {
                Log.d(logTag, "requestVisit, lampId $lampId")
                requestVisitUseCase(lampId)
            } catch (e: HttpException) {
                Log.e(logTag, "requestVisit HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "requestVisit Exception", e)
            }
        }
    }
}
