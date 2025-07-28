package com.devndev.lamp.presentation.ui.home.matchinghome.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.usecase.lamp.DeleteLampUseCase
import com.devndev.lamp.domain.usecase.lamp.ExitLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.lamp.KickUserUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StartMatchUseCase
import com.devndev.lamp.domain.usecase.lampmatch.StopMatchUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.GetUserStatusUseCase
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingHomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchingHomeViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getMyLampUseCase: GetMyLampUseCase,
    private val deleteLampUseCase: DeleteLampUseCase,
    private val exitLampUseCase: ExitLampUseCase,
    private val kickUserUseCase: KickUserUseCase,
    private val startMatchUseCase: StartMatchUseCase,
    private val stopMatchUseCase: StopMatchUseCase,
    private val getUSerStatusUseCase: GetUserStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MatchingHomeUiState())
    val uiState: StateFlow<MatchingHomeUiState> = _uiState.asStateFlow()

    init {
        getMyInfo()
        getUserStatus()
    }

    private fun getMyInfo() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { myInfo ->
                    Log.d(TAG, "getMyInfo Success")
                    _uiState.update { it.copy(myInfo = myInfo) }
                    getMyLamp()
                }
                .onFailure {
                    Log.e(TAG, "getMyInfo Failure", it)
                }
        }
    }

    fun getMyLamp() {
        viewModelScope.launch {
            getMyLampUseCase()
                .onSuccess { myLamp ->
                    Log.d(TAG, "getMyLamp Success")
                    _uiState.update { it.copy(myLamp = myLamp, isLoading = false) }
                    val isOwner =
                        uiState.value.myLamp?.lamp?.owner?.userId == uiState.value.myInfo?.userId

                    val isFullPersonnel =
                        uiState.value.myLamp?.lamp?.participants?.size?.plus(1) == uiState.value.myLamp?.lamp?.hopeMatchNumber
                    _uiState.update { it.copy(isOwner = isOwner, isFullPersonnel = isFullPersonnel) }
                }
                .onFailure {
                    Log.e(TAG, "getMyLamp Failure")
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun getUserStatus() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "getUserStatus")
                when (getUSerStatusUseCase().userLampStatus) {
                    "PREPARE" -> {
                        updateIsMatching(false)
                    }
                    "MATCHING" -> {
                        updateIsMatching(true)
                    }
                }
            } catch (e: HttpException) {
                Log.e(TAG, "getUserStatus HttpException", e)
            } catch (e: Exception) {
                Log.e(TAG, "getUserStatus Exception", e)
            }
        }
    }

    fun deleteLamp() {
        viewModelScope.launch {
            deleteLampUseCase()
                .onSuccess {
                    Log.d(TAG, "deleteLamp Success")
                }
                .onFailure {
                    Log.e(TAG, "deleteLamp Failure", it)
                }
        }
    }

    fun exitLamp() {
        viewModelScope.launch {
            exitLampUseCase()
                .onSuccess {
                    Log.d(TAG, "exitLamp Success")
                }
                .onFailure {
                    Log.e(TAG, "exitLamp Failure", it)
                }
        }
    }

    fun kickUser(kickUserId: Int) {
        viewModelScope.launch {
            kickUserUseCase(KickUserParam(kickUserId))
                .onSuccess {
                    Log.d(TAG, "kickUser Success")
                    getMyLamp()
                }
                .onFailure {
                    Log.e(TAG, "kickUseFailure")
                }
        }
    }

    fun cancelMatch() {
        viewModelScope.launch {
            startMatchUseCase()
                .onSuccess {
                    Log.d(TAG, "cancelMatch Success")
                    _uiState.update { it.copy(isMatching = false) }
                }
                .onFailure {
                    Log.d(TAG, "cancelMatch Failure")
                }
        }
    }

    fun startMatch() {
        viewModelScope.launch {
            startMatchUseCase()
                .onSuccess {
                    Log.d(TAG, "startMatch Success")
                    _uiState.update { it.copy(isMatching = true) }
                }
                .onFailure {
                    Log.d(TAG, "startMatch Failure")
                }
        }
    }

    fun stopMatch() {
        viewModelScope.launch {
            stopMatchUseCase()
                .onSuccess {
                    Log.d(TAG, "stopMatch Success")
                    _uiState.update { it.copy(isMatching = false) }
                }
                .onFailure {
                    Log.e(TAG, "stopMatch Failure")
                }
        }
    }

    fun updateIsMatching(isMatching: Boolean) {
        _uiState.update { it.copy(isMatching = isMatching) }
    }

    companion object {
        const val TAG = "MatchingHomeViewModel"
    }
}
