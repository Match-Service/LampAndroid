package com.devndev.lamp.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.user.PushTokenParam
import com.devndev.lamp.domain.usecase.config.GetIsFirstOpenUseCase
import com.devndev.lamp.domain.usecase.user.GetUserStatusUseCase
import com.devndev.lamp.domain.usecase.user.PutPushTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val putPushTokenUseCase: PutPushTokenUseCase,
    private val getIsFirstOpenUseCase: GetIsFirstOpenUseCase,
    private val getUserStatusUseCase: GetUserStatusUseCase
) : ViewModel() {
    private val logTag = "MainViewModel"

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    init {
        updateIsFirstOpen()
        getUserStatue()
    }

    private fun updateIsFirstOpen() {
        viewModelScope.launch {
            getIsFirstOpenUseCase()
                .onSuccess {
                    _state.update { state -> state.copy(isFirstOpen = it) }
                }.onFailure {
                    _state.update { state -> state.copy(isFirstOpen = false) }
                }
        }
    }

    fun putPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            viewModelScope.launch {
                Log.d(logTag, "putPushTokenUseCase()")
                val token = task.result
                putPushTokenUseCase(PushTokenParam(token))
            }
        }
    }

    private fun getUserStatue() {
        viewModelScope.launch {
            val userStatus = getUserStatusUseCase().userLampStatus
            _state.update { it.copy(userStatus = userStatus, isLoading = false) }
        }
    }

    fun updateIsAssessmentExist(isExist: Boolean) {
        _state.update { it.copy(isAssessmentListExist = isExist) }
    }
}
