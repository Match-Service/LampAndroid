package com.devndev.lamp.presentation.ui.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.ValidateNameParam
import com.devndev.lamp.domain.usecase.ValidateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase
) : ViewModel() {
    private val logTag = "RegistrationViewModel"

    private val _isDuplicateName = MutableStateFlow(false)
    val isDuplicateName: StateFlow<Boolean?> = _isDuplicateName

    fun updateIsDuplicateName(isDuplicate: Boolean) {
        _isDuplicateName.value = isDuplicate
        Log.d(logTag, "updateIsDuplicateName: $isDuplicateName")
    }

    fun checkIsDuplicateName(name: String) {
        viewModelScope.launch {
            val response = validateNameUseCase(ValidateNameParam(name))
            if (response.code() == 200) {
                Log.d(logTag, "checkIsDuplicateName: false")
                _isDuplicateName.value = false
            } else {
                Log.d(logTag, "checkIsDuplicateName: true")
                _isDuplicateName.value = true
            }
        }
    }
}
