package com.devndev.lamp.presentation.ui.appointment

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor() : ViewModel() {
    val _uiState = MutableStateFlow(AppointmentUiState())
    val uiState: StateFlow<AppointmentUiState> = _uiState.asStateFlow()

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun updateDate(date: String) {
        _uiState.update { it.copy(date = date) }
    }
}
