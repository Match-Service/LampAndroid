package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.GetMyInfoUseCase
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : ViewModel() {
    private val logTag = "MyPageViewModel"

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
            } catch (e: Exception) {
                Log.e(logTag, "fetchData Exception", e)
            }
        }
    }

    fun signOut() {
        Log.d(logTag, "signOut()")
        googleSignInClient.signOut().addOnCompleteListener {
            AuthManager.updateLoginStatus(false)
            AuthManager.updateAccountStatus(AccountStatus.NONE)

            Log.d(logTag, "signOut() isLoggedIn ${AuthManager.isLoggedIn.value}")
        }
    }
}
