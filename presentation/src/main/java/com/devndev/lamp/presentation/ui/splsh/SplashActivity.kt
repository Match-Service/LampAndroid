package com.devndev.lamp.presentation.ui.splsh

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.login.LoginActivity
import com.devndev.lamp.presentation.ui.main.MainActivity
import com.devndev.lamp.presentation.utils.IconStatusManager
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

    override fun onDestroy() {
        super.onDestroy()
        setAppIcon(IconStatusManager.getIconStatus())
    }

    private fun setAppIcon(gender: String) {
        Log.d("SplashActivity", "setAppIcon()")
        val packageManager = this.packageManager

        // Correct ComponentName with fully qualified class names
        val aliases = listOf(
            "com.devndev.lamp.presentation.ui.splsh.SplashActivity",
            "com.devndev.lamp.presentation.ui.splsh.SplashActivityMale",
            "com.devndev.lamp.presentation.ui.splsh.SplashActivityFemale"
        )

        // Disable all aliases first
        aliases.forEach { alias ->
            packageManager.setComponentEnabledSetting(
                ComponentName(this, alias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        // Enable the correct alias based on gender
        val targetAlias = when (gender) {
            "MALE" -> "com.devndev.lamp.presentation.ui.splsh.SplashActivityMale"
            "FEMALE" -> "com.devndev.lamp.presentation.ui.splsh.SplashActivityFemale"
            else -> "com.devndev.lamp.presentation.ui.splsh.SplashActivity"
        }
        packageManager.setComponentEnabledSetting(
            ComponentName(this, targetAlias),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}
