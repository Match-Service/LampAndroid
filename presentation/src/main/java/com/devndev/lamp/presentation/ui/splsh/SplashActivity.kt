package com.devndev.lamp.presentation.ui.splsh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.login.LoginActivity
import com.devndev.lamp.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkUserLoggedIn()
        setContent {
            LampTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                SplashScreen()

                when {
                    state.isUserLoggedIn == true -> {
                        MainActivity.openActivity(this)
                        finish()
                    }
                    state.isUserLoggedIn == false -> {
                        LoginActivity.openActivity(this)
                        finish()
                    }
                    else -> Unit
                }
            }
        }
    }
}
