
package com.devndev.lamp.presentation.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.login.navigation.LoginNavHost
import com.devndev.lamp.presentation.ui.main.MainActivity
import com.devndev.lamp.presentation.ui.onboarding.OnBoardingActivity
import com.devndev.lamp.presentation.ui.registration.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val logTag = "LoginActivity"
    private val loginViewModel by viewModels<LoginViewModel>()
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val keyboardController = LocalSoftwareKeyboardController.current
            val loginState by loginViewModel.uiState.collectAsStateWithLifecycle()
            val registrationState by registrationViewModel.uiState.collectAsStateWithLifecycle()
            val navController = rememberNavController()
            LampTheme() {
                Scaffold(
                    containerColor = LampBlack,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            keyboardController?.hide()
                        })
                    }
                ) { innerPadding ->
                    LoginNavHost(
                        onClickSignInButton = loginViewModel::signInWithGoogle,
                        onClickStartButton = registrationViewModel::uploadImages,
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Route.LOGIN
                    )
                }
                if (loginState.isUserLoggedIn) {
                    Log.d(logTag, "isUserLoggedIn true")
                    if (loginState.isFirstOpen == true) {
                        OnBoardingActivity.openActivity(this)
                    } else {
                        MainActivity.openActivity(this)
                    }
                    finish()
                }
                if (registrationState.isSignedUp) {
                    Log.d(logTag, "isSignedUp")
                    OnBoardingActivity.openActivity(this)
                    finish()
                }
            }
        }
    }

    companion object {
        fun openActivity(context: Activity) {
            context.startActivity(
                Intent(context, LoginActivity::class.java)
            )
        }
    }
}
