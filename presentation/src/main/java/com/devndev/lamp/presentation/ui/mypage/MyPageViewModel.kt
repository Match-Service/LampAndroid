package com.devndev.lamp.presentation.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {
    private val logTag = "MyPageViewModel"

    fun signOut() {
        Log.d(logTag, "signOut()")
        googleSignInClient.signOut().addOnCompleteListener {
            AuthManager.updateLoginStatus(false)
            Log.d(logTag, "signOut() isLoggedIn ${AuthManager.isLoggedIn.value}")
        }
    }
}
