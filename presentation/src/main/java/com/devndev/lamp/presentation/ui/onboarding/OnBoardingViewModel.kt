package com.devndev.lamp.presentation.ui.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.usecase.config.SaveIsFirstOpenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val saveIsFirstOpenUseCase: SaveIsFirstOpenUseCase
) : ViewModel() {
    fun saveIsNotFirstOpen(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            saveIsFirstOpenUseCase(false)
                .onSuccess {
                    onSuccess()
                }
                .onFailure {
                    Log.e(TAG, "saveIsFirstOpenUseCase Failure", it)
                }
        }
    }
    companion object {
        const val TAG = "OnBoardingViewModel"
    }
}
