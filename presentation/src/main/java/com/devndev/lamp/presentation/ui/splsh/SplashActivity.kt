package com.devndev.lamp.presentation.ui.splsh

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.login.LoginActivity
import com.devndev.lamp.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel by viewModels<SplashViewModel>()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        viewModel.checkUserLoggedIn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotificationPermission()
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

    @SuppressLint("InlinedApi")
    private fun checkNotificationPermission() {
        if (askNotificationPermission()) {
            viewModel.checkUserLoggedIn()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun askNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }
}
