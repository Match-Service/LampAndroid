package com.devndev.lamp.presentation.ui.creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.CreateLampParam
import com.devndev.lamp.domain.usecase.CreateLampUseCase
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LampCreationViewModel @Inject constructor(
    private val createLampUseCase: CreateLampUseCase
) : ViewModel() {
    private val logTag = "LampCreationViewModel"

    fun createLamp(createLampParam: CreateLampParam) {
        viewModelScope.launch {
            try {
                Log.d(logTag, "createLamp: $createLampParam")
                createLampUseCase(createLampParam)
            } catch (e: ApiException) {
                Log.e(logTag, "createLamp", e)
            }
        }
    }
}
