package com.devndev.lamp.presentation.ui.appointment.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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

fun NavController.navigateRegisterAppointment(
    navOptions: NavOptions? = null,
    isEdit: Boolean,
    chatAppointmentId: Int
) {
    val route = "register_appointment/$isEdit/$chatAppointmentId"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.registerAppointmentNavGraph(
    chatRoomId: Int,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.REGISTER_APPOINTMENT,
        arguments = listOf(
            navArgument("isEdit") { type = NavType.BoolType },
            navArgument("chatAppointmentId") { type = NavType.IntType }
        ),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
        val chatAppointmentId = backStackEntry.arguments?.getInt("chatAppointmentId") ?: -1
        RegisterAppointmentScreen(
            modifier = modifier.padding(padding),
            isEdit = isEdit,
            navController = navController,
            chatRoomId = chatRoomId,
            chatAppointmentId = chatAppointmentId
        )
    }
}
