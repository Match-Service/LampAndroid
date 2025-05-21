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
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateAppointment(navOptions: NavOptions? = null) {
    this.navigate(Route.APPOINTMENT, navOptions)
}

fun NavGraphBuilder.appointmentNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.APPOINTMENT,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        AppointmentScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
