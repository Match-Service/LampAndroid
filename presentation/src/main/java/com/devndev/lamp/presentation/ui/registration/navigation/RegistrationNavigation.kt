package com.devndev.lamp.presentation.ui.registration.navigation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.registration.RegistrationScreen

fun NavController.navigateRegistration(navOptions: NavOptions? = null) {
    this.navigate(Route.REGISTRATION, navOptions)
}

fun NavGraphBuilder.registrationNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.REGISTRATION,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) }
    ) {
        RegistrationScreen(modifier = modifier, navController = navController)
    }
}
