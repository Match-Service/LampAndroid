package com.devndev.lamp.presentation.ui.appointment.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.appointment.AppointmentScreen
import com.devndev.lamp.presentation.ui.appointment.register.RegisterAppointmentScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateAppointment(navOptions: NavOptions? = null) {
    this.navigate(Route.APPOINTMENT, navOptions)
}

fun NavGraphBuilder.appointmentNavGraph(
    chatRoomId: Int,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.APPOINTMENT,
        enterTransition = {
            if (initialState.destination.route == Route.REGISTER_APPOINTMENT) {
                null
            } else {
                slideInHorizontally(initialOffsetX = { it })
            }
        },
        exitTransition = {
            if (targetState.destination.route != Route.REGISTER_APPOINTMENT) {
                slideOutHorizontally(targetOffsetX = { it })
            } else {
                null
            }
        }
    ) {
        AppointmentScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            chatRoomId = chatRoomId
        )
    }
}

fun NavController.navigateRegisterAppointment(navOptions: NavOptions? = null) {
    this.navigate(Route.REGISTER_APPOINTMENT, navOptions)
}

fun NavGraphBuilder.registerAppointmentNavGraph(
    chatRoomId: Int,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.REGISTER_APPOINTMENT,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        RegisterAppointmentScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            chatRoomId = chatRoomId
        )
    }
}
