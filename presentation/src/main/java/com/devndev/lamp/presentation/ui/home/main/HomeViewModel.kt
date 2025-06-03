package com.devndev.lamp.presentation.ui.home.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.lamp.CancelVisitRequestUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetVisitRequestLampInfoUseCase
import com.devndev.lamp.domain.usecase.lampmatch.GetMatchSuggestionUseCase
import com.devndev.lamp.domain.usecase.socket.AddStatusListenerUseCase
import com.devndev.lamp.domain.usecase.socket.RemoveStatueListenerUseCase
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
    private val getMatchSuggestionUseCase: GetMatchSuggestionUseCase,
    private val getUserStatusUseCase: GetUserStatusUseCase,
    private val getVisitRequestLampInfoUseCase: GetVisitRequestLampInfoUseCase,
    private val cancelVisitRequestUseCase: CancelVisitRequestUseCase,
    private val acceptUseCase: AcceptVoteUseCase,
    private val rejectUseCase: RejectVoteUseCase,
    private val addStatusListenerUseCase: AddStatusListenerUseCase,
    private val removeStatueListenerUseCase: RemoveStatueListenerUseCase
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
            getMyInfoUseCase()
                .onSuccess { userInfo ->
                    Log.d(TAG, "fetchData")
                    _myInfo.value = userInfo
                    Log.d(TAG, "My Info ${myInfo.value}")
                    _myInfo.value?.gender?.let {
                        IconStatusManager.setIconStatus(it)
                    }
                }
                .onFailure { throwable ->
                    Log.e(TAG, "Failed to fetch user info", throwable)
                }
        }
    }

    fun getLampData() {
        viewModelScope.launch {
            getMyLampUseCase()
                .onSuccess { myLamp ->
                    Log.d(TAG, "getLampData Success")
                    _myLamp.value = myLamp
                }
                .onFailure {
                    Log.e(TAG, "getLampData", it)
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

    fun addStatusListener() {
        getUserStatus()
        getLampData()
        viewModelScope.launch {
            try {
                Log.d(TAG, "addStatusListener")
                addStatusListenerUseCase(
                    onMessage = { message ->
                        _userStatus.value = message
                        Log.d(TAG, "status: ${userStatue.value}")
                    },
                    onUpdatedMessage = {
                        Log.d(TAG, "getLampData: ${myLamp.value}")
                        getLampData()
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "addStatusListener failed", e)
            }
        }
    }

    fun removeStatusListener() {
        viewModelScope.launch {
            removeStatueListenerUseCase()
            Log.d(TAG, "removeStatusListener")
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
