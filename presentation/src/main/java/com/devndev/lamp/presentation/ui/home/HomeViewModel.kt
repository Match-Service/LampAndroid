package com.devndev.lamp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.manager.AppIconManager
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.GetMyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appIconManager: AppIconManager,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    private val logTag = "HomeViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "fetchData")
                _myInfo.value = getMyInfoUseCase()
                Log.d(logTag, "My Info ${myInfo.value}")
                _myInfo.value?.gender?.let {
                    appIconManager.saveIconPreference(it)
                }
            } catch (e: HttpException) {
                Log.e(logTag, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }
}
