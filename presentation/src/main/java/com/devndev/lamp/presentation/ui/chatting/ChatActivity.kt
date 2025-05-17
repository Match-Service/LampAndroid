package com.devndev.lamp.presentation.ui.chatting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.chatting.navigation.ChatNavHost
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.main.LampTopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatRoomId = intent.getIntExtra("chatRoomId", 0)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            LampTheme() {
                Scaffold(
                    topBar = {
                        when (currentRoute) {
                            Route.CHAT -> {
                                LampTopBar(
                                    navController = navController,
                                    isAlarmIconNeed = true,
                                    color = Gray
                                )
                            }

                            else -> {
                                LampTopBar(
                                    navController = navController,
                                    isAlarmIconNeed = true
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    ChatNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Route.CHAT,
                        chatRoomId = chatRoomId
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "ChatActivity"
    }
}
