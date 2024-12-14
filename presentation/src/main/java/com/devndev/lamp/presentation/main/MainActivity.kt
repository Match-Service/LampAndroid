package com.devndev.lamp.presentation.main

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.splsh.SplashScreen
import com.devndev.lamp.presentation.ui.theme.LampTheme
import com.devndev.lamp.presentation.ui.utils.IconStatusManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logTag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LampTheme {
                val loginViewModel: LoginViewModel = hiltViewModel()
                Lamp(viewModel = loginViewModel)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(logTag, "onStop")
        setAppIcon(IconStatusManager.getIconStatus())
    }

    private fun setAppIcon(gender: String) {
        Log.d(logTag, "setAppIcon()")
        val packageManager = this.packageManager

        // Correct ComponentName with fully qualified class names
        val aliases = listOf(
            "com.devndev.lamp.presentation.main.MainActivity",
            "com.devndev.lamp.MainActivityMale",
            "com.devndev.lamp.MainActivityFemale"
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
            "MALE" -> "com.devndev.lamp.MainActivityMale"
            "FEMALE" -> "com.devndev.lamp.MainActivityFemale"
            else -> "com.devndev.lamp.presentation.main.MainActivity"
        }

        packageManager.setComponentEnabledSetting(
            ComponentName(this, targetAlias),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}

@Composable
fun Lamp(viewModel: LoginViewModel) {
    var showSplash by remember { mutableStateOf(true) }
    if (showSplash) {
        SplashScreen {
            showSplash = false // 애니메이션이 끝나면 메인 화면을 표시
        }
    } else {
        // 애니메이션 후 메인 화면 표시
        MainScreen(modifier = Modifier)
    }
}
