package com.devndev.lamp.presentation.ui.home.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.lamp.DeleteLampUseCase
import com.devndev.lamp.domain.usecase.lamp.ExitLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.lamp.KickUserUseCase
import com.devndev.lamp.domain.usecase.lampmatch.GetMatchSuggestionUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StartMatchUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StopMatchUseCase
import com.devndev.lamp.domain.usecase.socket.ConnectSocketUseCase
import com.devndev.lamp.domain.usecase.socket.DisconnectSocketUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.GetUserStatusUseCase
import com.devndev.lamp.presentation.utils.IconStatusManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getMyLampUseCase: GetMyLampUseCase,
    private val deleteLampUseCase: DeleteLampUseCase,
    private val exitLampUseCase: ExitLampUseCase,
    private val kickUserUseCase: KickUserUseCase,
    private val startMatchUseCase: StartMatchUseCase,
    private val stopMatchUseCase: StopMatchUseCase,
    private val getMatchSuggestionUseCase: GetMatchSuggestionUseCase,
    private val getUserStatusUseCase: GetUserStatusUseCase,
    private val connectSocketUseCase: ConnectSocketUseCase,
    private val disconnectSocketUseCase: DisconnectSocketUseCase
) : ViewModel() {
    private val logTag = "HomeViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _myLamp = MutableStateFlow<LampDomainModel?>(null)
    val myLamp: StateFlow<LampDomainModel?> = _myLamp

    private val _matchSuggestion = MutableStateFlow<MatchSuggestionDomainModel?>(null)
    val matchSuggestion: StateFlow<MatchSuggestionDomainModel?> = _matchSuggestion

    private val _userStatus = MutableStateFlow<String>("")
    val userStatue: StateFlow<String> = _userStatus

    init {
        getMyInfo()
        getLampData()
        getUserStatus()
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "fetchData")
                _myInfo.value = getMyInfoUseCase()
                Log.d(logTag, "My Info ${myInfo.value}")
                _myInfo.value?.gender?.let {
                    IconStatusManager.setIconStatus(it)
                }
            } catch (e: HttpException) {
                Log.e(logTag, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }

    fun getLampData() {
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

    fun deleteLamp() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "deleteLamp()")
                myLamp.value?.lamp?.lampId?.let { deleteLampUseCase(it) }
                getUserStatus()
            } catch (e: HttpException) {
                Log.e(logTag, "deleteLamp HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "deleteLamp Exception", e)
            }
        }
    }

    fun exitLamp() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "exitLamp()")
                myLamp.value?.lamp?.lampId?.let { exitLampUseCase(it) }
                getLampData()
            } catch (e: Exception) {
                Log.e(logTag, "exitLamp Exception", e)
            }
        }
    }

    fun kickUser(kickUserId: Int) {
        viewModelScope.launch {
            try {
                Log.d(logTag, "kickUser()")
                myLamp.value?.lamp?.lampId?.let {
                    kickUserUseCase(it, KickUserParam(kickUserId))
                    Log.d(logTag, "kickUser lampId $it, kickUserId $kickUserId")
                }
                getLampData()
            } catch (e: Exception) {
                Log.e(logTag, "kickUser Exception", e)
            }
        }
    }

    fun startMatch() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "startMatch()")
                startMatchUseCase()
                getLampData()
            } catch (e: Exception) {
                Log.e(logTag, "startMatch Exception", e)
            }
        }
    }

    fun stopMatch() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "stopMatch")
                stopMatchUseCase()
                getLampData()
            } catch (e: Exception) {
                Log.e(logTag, "stopMatchException", e)
            }
        }
    }

    fun getMatchSuggestion() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "getMatchSuggestion")
                _matchSuggestion.value = getMatchSuggestionUseCase()
                Log.d(logTag, "MatchSuggestion ${matchSuggestion.value}")
            } catch (e: HttpException) {
                Log.e(logTag, "getMatchSuggestion HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "getMatchSuggestion Exception", e)
            }
        }
    }

    private fun getUserStatus() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "getUserStatus")
                _userStatus.value = getUserStatusUseCase().userLampStatus
                Log.d(logTag, "UserStatus ${userStatue.value}")
            } catch (e: HttpException) {
                Log.e(logTag, "getUserStatus HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "getUserStatus Exception", e)
            }
        }
    }

    fun connectSocket() {
        viewModelScope.launch {
            try {
                connectSocketUseCase(
                    onConnected = {
                        Log.d(logTag, "Connected to socket")
                    },
                    onMessage = { message ->
                        _userStatus.value = message
                        Log.d(logTag, "status: ${userStatue.value}")
                    }
                )
            } catch (e: Exception) {
                Log.e(logTag, "Socket connection failed", e)
            }
        }
    }

    fun disconnectSocket() {
        viewModelScope.launch {
            disconnectSocketUseCase()
            Log.d(logTag, "Disconnect socket")
        }
    }
}
