package com.devndev.lamp.presentation.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devndev.lamp.data.socket.LampSocketService
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.login.LoginActivity
import com.devndev.lamp.presentation.ui.mypage.MyPageViewModel
import com.devndev.lamp.presentation.ui.onboarding.OnBoardingActivity
import com.devndev.lamp.presentation.utils.IconStatusManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logTag = "MainActivity"

    @Inject
    lateinit var lampSocketService: LampSocketService

    private val mainViewModel by viewModels<MainViewModel>()
    private val myPageViewModel by viewModels<MyPageViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (!it) {
            myPageViewModel.rejectPushSetting()
        }
        OnBoardingActivity.openActivity(this)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lampSocketService.connect {
            Log.d(logTag, "Connect Socket")
        }
        mainViewModel.putPushToken()
        setContent {
            val state by myPageViewModel.uiState.collectAsStateWithLifecycle()
            val mainState by mainViewModel.state.collectAsStateWithLifecycle()
            LampTheme {
                when {
                    mainState.isFirstOpen == true -> {
                        checkNotificationPermission()
                    }
                    mainState.isFirstOpen == false -> {
                        Lamp(
                            signOut = myPageViewModel::signOut
                        )
                    }
                }

                if (state.isLoggedOut) {
                    Log.d(logTag, "isUserLoggedOut true")
                    LoginActivity.openActivity(this)
                    finish()
                }
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
            "com.devndev.lamp.presentation.ui.splsh.SplashActivity",
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
            else -> "com.devndev.lamp.presentation.ui.splsh.SplashActivity"
        }

        packageManager.setComponentEnabledSetting(
            ComponentName(this, targetAlias),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    @SuppressLint("InlinedApi")
    private fun checkNotificationPermission() {
        if (!askNotificationPermission()) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            OnBoardingActivity.openActivity(this)
            finish()
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

    companion object {
        fun openActivity(context: Activity) {
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        }

        fun openActivity(context: Activity, flags: Int) {
            context.startActivity(
                Intent(context, MainActivity::class.java).apply {
                    addFlags(flags)
                }
            )
        }
    }
}

@Composable
fun Lamp(signOut: () -> Unit) {
    MainScreen(modifier = Modifier, signOut = signOut)
}
