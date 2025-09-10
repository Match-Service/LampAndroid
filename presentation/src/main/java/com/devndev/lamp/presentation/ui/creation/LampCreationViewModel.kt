package com.devndev.lamp.presentation.ui.creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.usecase.lamp.CreateLampUseCase
import com.devndev.lamp.domain.usecase.lamp.EditLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LampCreationViewModel @Inject constructor(
    private val getMyLampUseCase: GetMyLampUseCase,
    private val createLampUseCase: CreateLampUseCase,
    private val editLampUseCase: EditLampUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LampCreationUiState())
    val uiState: StateFlow<LampCreationUiState> = _uiState.asStateFlow()

    fun getMyLamp(
        onSuccess: (Boolean, Int) -> Unit = { _, _ -> }
    ) {
        viewModelScope.launch {
            getMyLampUseCase()
                .onSuccess { lamp ->
                    Log.d(TAG, "getMyLamp Success")
                    val personnel = lamp.lamp?.hopeMatchNumber
                    _uiState.update {
                        it.copy(
                            personnel = "$personnel:$personnel",
                            region = convertLocation(lamp.lamp?.location ?: ""),
                            mood = convertMoodToInt(lamp.lamp?.color ?: ""),
                            lampName = lamp.lamp?.name ?: "",
                            lampSummary = lamp.lamp?.description ?: ""
                        )
                    }
                    onSuccess(true, lamp.lamp?.participants?.size ?: 0)
                }.onFailure {
                    Log.e(TAG, "getMyLamp Failure", it)
                    onSuccess(false, 0)
                }
        }
    }

    fun createLamp(
        createLampParam: CreateLampParam,
        onSuccess: () -> Unit
    ) {
        Log.d(TAG, "createLamp: $createLampParam")
        viewModelScope.launch {
            createLampUseCase(createLampParam)
                .onSuccess {
                    Log.d(TAG, "createLamp success")
                    onSuccess()
                }
                .onFailure {
                    Log.e(TAG, "createLamp failure", it)
                }
        }
    }

    fun editLamp(
        hopeMatchNumber: Int,
        editLampParam: CreateLampParam,
        onSuccess: (Boolean, Int) -> Unit
    ) {
        getMyLamp { success, participantSize ->
            if (success) {
                if (hopeMatchNumber < participantSize) {
                    onSuccess(false, 400)
                } else {
                    viewModelScope.launch {
                        editLampUseCase(editLampParam)
                            .onSuccess {
                                Log.d(TAG, "editLamp Success")
                                onSuccess(true, 0)
                            }
                            .onFailure {
                                Log.e(TAG, "editLamp failure", it)
                                onSuccess(false, 400)
                            }
                    }
                }
            }
        }
    }

    fun updatePersonnel(personnel: String) {
        _uiState.update { it.copy(personnel = personnel) }
    }

    fun updateRegion(region: String) {
        _uiState.update { it.copy(region = region) }
    }

    fun updateMood(mood: Int) {
        _uiState.update { it.copy(mood = mood) }
    }

    fun updateLampName(name: String) {
        _uiState.update { it.copy(lampName = name) }
    }

    fun updateLampSummary(summary: String) {
        _uiState.update { it.copy(lampSummary = summary) }
    }

    private fun convertLocation(regionCode: String): String {
        return when (regionCode) {
            "KONDA_SEOUNGSU" -> "건대·성수"
            "SINCHON_HONGDAE" -> "신촌·홍대"
            "GANGNAM_JAMSIL" -> "강남·잠실"
            "INCHEON" -> "인천"
            "GYEONGGI" -> "경기"
            else -> ""
        }
    }

    private fun convertMoodToInt(mood: String): Int {
        return when (mood) {
            "FUNNY" -> 1
            "CASUAL" -> 2
            "SERIOUS" -> 3
            else -> 0
        }
    }

    companion object {
        const val TAG = "LampCreationViewModel"
    }
}
