package com.devndev.lamp.presentation.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.data.socket.LampSocketService
import com.devndev.lamp.domain.model.alarm.AlarmMessageType
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.alarm.navigation.navigateAlarm
import com.devndev.lamp.presentation.ui.chatting.navigation.navigateChatList
import com.devndev.lamp.presentation.ui.login.LoginActivity
import com.devndev.lamp.presentation.ui.mypage.MyPageViewModel
import com.devndev.lamp.presentation.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logTag = "MainActivity"

    @Inject
    lateinit var lampSocketService: LampSocketService

    private lateinit var navController: NavHostController

    private val mainViewModel by viewModels<MainViewModel>()
    private val myPageViewModel by viewModels<MyPageViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (!it) {
            myPageViewModel.rejectPushSetting()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lampSocketService.connect {
            Log.d(logTag, "Connect Socket")
        }

        val targetScreen = AlarmMessageType.from(intent?.getStringExtra("navigate_target"))
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
                        navController = rememberNavController()
                        Lamp(
                            navController = navController,
                            signOut = myPageViewModel::signOut,
                            targetScreen = targetScreen
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val targetScreen = AlarmMessageType.from(intent?.getStringExtra("navigate_target")) ?: return
        when (targetScreen) {
            AlarmMessageType.INVITE_REQUEST,
            AlarmMessageType.VISIT_REQUEST -> {
                navController.navigateAlarm()
            }
            AlarmMessageType.CHAT -> {
                navController.navigateChatList()
            }
            else -> {}
        }
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
fun Lamp(navController: NavHostController, signOut: () -> Unit, targetScreen: AlarmMessageType?) {
    MainScreen(
        navController = navController,
        modifier = Modifier,
        signOut = signOut,
        targetScreen = targetScreen
    )
}
