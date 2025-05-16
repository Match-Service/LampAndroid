package com.devndev.lamp.presentation.ui.home.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.chat.TestChatUseCase
import com.devndev.lamp.domain.usecase.lamp.CancelVisitRequestUseCase
import com.devndev.lamp.domain.usecase.lamp.DeleteLampUseCase
import com.devndev.lamp.domain.usecase.lamp.ExitLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetVisitRequestLampInfoUseCase
import com.devndev.lamp.domain.usecase.lamp.KickUserUseCase
import com.devndev.lamp.domain.usecase.lampmatch.GetMatchSuggestionUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StartMatchUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StopMatchUseCase
import com.devndev.lamp.domain.usecase.socket.ConnectSocketUseCase
import com.devndev.lamp.domain.usecase.socket.DisconnectSocketUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.GetUserStatusUseCase
import com.devndev.lamp.domain.usecase.vote.AcceptVoteUseCase
import com.devndev.lamp.domain.usecase.vote.RejectVoteUseCase
import com.devndev.lamp.presentation.ui.chatting.ChatViewModel
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
    private val disconnectSocketUseCase: DisconnectSocketUseCase,
    private val getVisitRequestLampInfoUseCase: GetVisitRequestLampInfoUseCase,
    private val cancelVisitRequestUseCase: CancelVisitRequestUseCase,
    private val testChatUseCase: TestChatUseCase,
    private val acceptUseCase: AcceptVoteUseCase,
    private val rejectUseCase: RejectVoteUseCase
) : ViewModel() {

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _myLamp = MutableStateFlow<LampDomainModel?>(null)
    val myLamp: StateFlow<LampDomainModel?> = _myLamp

    private val _matchSuggestion = MutableStateFlow<MatchSuggestionDomainModel?>(null)
    val matchSuggestion: StateFlow<MatchSuggestionDomainModel?> = _matchSuggestion

    private val _userStatus = MutableStateFlow<String>("")
    val userStatue: StateFlow<String> = _userStatus

    private val _visitLampOwnerName = MutableStateFlow<String>("")
    val visitLampOwnerName = _visitLampOwnerName

    init {
        getMyInfo()
        getLampData()
        getUserStatus()
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "fetchData")
                _myInfo.value = getMyInfoUseCase()
                Log.d(TAG, "My Info ${myInfo.value}")
                _myInfo.value?.gender?.let {
                    IconStatusManager.setIconStatus(it)
                }
            } catch (e: HttpException) {
                Log.e(TAG, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "fetchData Exception", e)
            }
        }
    }

    fun getLampData() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getLampData")
                _myLamp.value = getMyLampUseCase()
                Log.d(TAG, "My Lamp ${myLamp.value}")
            } catch (e: HttpException) {
                Log.e(TAG, "getLampData HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "getLampData Exception", e)
            }
        }
    }

    fun deleteLamp() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "deleteLamp()")
                deleteLampUseCase()
                getUserStatus()
            } catch (e: HttpException) {
                Log.e(TAG, "deleteLamp HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "deleteLamp Exception", e)
            }
        }
    }

    fun exitLamp() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "exitLamp()")
                exitLampUseCase()
                getLampData()
            } catch (e: Exception) {
                Log.e(TAG, "exitLamp Exception", e)
            }
        }
    }

    fun kickUser(kickUserId: Int) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "kickUser()")
                kickUserUseCase(KickUserParam(kickUserId))
                Log.d(TAG, "kickUser kickUserId $kickUserId")
                getLampData()
            } catch (e: Exception) {
                Log.e(TAG, "kickUser Exception", e)
            }
        }
    }

    fun startMatch() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "startMatch()")
                startMatchUseCase()
                getLampData()
                testChat(myLamp.value?.lamp?.lampId ?: 0)
            } catch (e: Exception) {
                Log.e(TAG, "startMatch Exception", e)
            }
        }
    }

    fun stopMatch() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "stopMatch")
                stopMatchUseCase()
                getLampData()
            } catch (e: Exception) {
                Log.e(TAG, "stopMatchException", e)
            }
        }
    }

    fun getMatchSuggestion() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getMatchSuggestion")
                _matchSuggestion.value = getMatchSuggestionUseCase()
                Log.d(TAG, "MatchSuggestion ${matchSuggestion.value}")
            } catch (e: HttpException) {
                Log.e(TAG, "getMatchSuggestion HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "getMatchSuggestion Exception", e)
            }
        }
    }

    private fun getUserStatus() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getUserStatus")
                _userStatus.value = getUserStatusUseCase().userLampStatus
                Log.d(TAG, "UserStatus ${userStatue.value}")
            } catch (e: HttpException) {
                Log.e(TAG, "getUserStatus HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "getUserStatus Exception", e)
            }
        }
    }

    fun connectSocket() {
        viewModelScope.launch {
            try {
                connectSocketUseCase(
                    onConnected = {
                        Log.d(TAG, "Connected to socket")
                    },
                    onMessage = { message ->
                        _userStatus.value = message
                        Log.d(TAG, "status: ${userStatue.value}")
                    },
                    onUpdatedMessage = {
                        getLampData()
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Socket connection failed", e)
            }
        }
    }

    fun disconnectSocket() {
        viewModelScope.launch {
            disconnectSocketUseCase()
            Log.d(TAG, "Disconnect socket")
        }
    }

    fun getVisitLampOwnerName() {
        viewModelScope.launch {
            val name = getVisitRequestLampInfoUseCase()
            Log.d(TAG, "getVisitLampOwnerName $name")
            _visitLampOwnerName.value = name
        }
    }

    fun cancelVisitRequest() {
        viewModelScope.launch {
            Log.d(TAG, "cancelVisitRequest")
            cancelVisitRequestUseCase()
        }
    }

    fun testChat(lampId: Int) {
        viewModelScope.launch {
            Log.d(TAG, "testChat")
            testChatUseCase(lampId)
        }
    }

    fun accept(
        lampSuggestionId: Int
    ) {
        viewModelScope.launch {
            try {
                Log.d(ChatViewModel.TAG, "acceptVote")
                acceptUseCase(lampSuggestionId)
            } catch (e: Exception) {
                Log.e(ChatViewModel.TAG, "accept Exception", e)
            }
        }
    }

    fun reject(
        lampSuggestionId: Int
    ) {
        viewModelScope.launch {
            try {
                Log.d(ChatViewModel.TAG, "rejectVote")
                rejectUseCase(lampSuggestionId)
            } catch (e: Exception) {
                Log.e(ChatViewModel.TAG, "reject Exception", e)
            }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}
