package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devndev.lamp.presentation.ui.alarm.navigation.alarmNavGraph
import com.devndev.lamp.presentation.ui.appointment.navigation.appointmentNavGraph
import com.devndev.lamp.presentation.ui.appointment.navigation.registerAppointmentNavGraph

@Composable
fun ChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    chatRoomId: Int,
    padding: PaddingValues
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        chatNavGraph(
            padding = PaddingValues(),
            navController = navController,
            chatRoomId = chatRoomId
        )
        alarmNavGraph(
            padding = PaddingValues(),
            navController = navController
        )
        appointmentNavGraph(
            padding = padding,
            modifier = modifier,
            navController = navController
        )
        registerAppointmentNavGraph(
            padding = padding,
            modifier = modifier,
            navController = navController
        )
    }
}
