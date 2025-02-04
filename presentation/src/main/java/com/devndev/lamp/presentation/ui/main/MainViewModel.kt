package com.devndev.lamp.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.user.PushTokenParam
import com.devndev.lamp.domain.usecase.user.PutPushTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val putPushTokenUseCase: PutPushTokenUseCase
) : ViewModel() {
    private val logTag = "MainViewModel"

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
}
