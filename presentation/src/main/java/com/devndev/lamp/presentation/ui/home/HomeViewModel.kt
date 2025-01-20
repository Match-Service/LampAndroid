package com.devndev.lamp.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.lamp.DeleteLampUseCase
import com.devndev.lamp.domain.usecase.lamp.GetMyLampUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.presentation.ui.utils.IconStatusManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getMyLampUseCase: GetMyLampUseCase,
    private val deleteLampUseCase: DeleteLampUseCase
) : ViewModel() {
    private val logTag = "HomeViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _myLamp = MutableStateFlow<LampDomainModel?>(null)
    val myLamp: StateFlow<LampDomainModel?> = _myLamp

    init {
        fetchData()
        getLampData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "fetchData")
                _myInfo.value = getMyInfoUseCase()
                Log.d(logTag, "My Info ${myInfo.value}")
                _myInfo.value?.gender?.let {
                    IconStatusManager.setIconStatus(it)
                }
            } catch (e: HttpException) {
                Log.e(logTag, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }

    private fun getLampData() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "getLampData")
                _myLamp.value = getMyLampUseCase()
                Log.d(logTag, "My Lamp ${myLamp.value}")
            } catch (e: HttpException) {
                Log.e(logTag, "getLampData HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "getLampData Exception", e)
            }
        }
    }

    fun deleteLamp() {
        viewModelScope.launch {
            try {
                Log.d(logTag, "deleteLamp()")
                myLamp.value?.lamp?.lampId?.let { deleteLampUseCase(it) }
                getLampData()
            } catch (e: HttpException) {
                Log.e(logTag, "deleteLamp HttpException", e)
            } catch (e: Exception) {
                Log.e(logTag, "deleteLamp Exception", e)
            }
        }
    }
}
