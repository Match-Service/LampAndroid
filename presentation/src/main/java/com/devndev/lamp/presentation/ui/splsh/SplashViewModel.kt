package com.devndev.lamp.presentation.ui.splsh

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.usecase.login.CheckUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {
    private val logTag = "SplashViewModel"

    private val _state = MutableStateFlow(SplashUiState())
    val state = _state.asStateFlow()

    fun checkUserLoggedIn() {
        _state.update { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            val isUserLoggedIn = checkUserLoggedInUseCase()
            delay(3000L)
            Log.d(logTag, isUserLoggedIn.toString())
            _state.update { state ->
                state.copy(
                    isUserLoggedIn = isUserLoggedIn,
                    isLoading = false
                )
            }
        }
    }
}
