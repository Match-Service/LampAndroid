package com.devndev.lamp.presentation.ui.signup.navigation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.signup.SignUpScreen
import com.devndev.lamp.presentation.ui.signup.StartLampScreen

fun NavController.navigateSignUp(navOptions: NavOptions? = null) {
    this.navigate(Route.SIGNUP, navOptions)
}

fun NavGraphBuilder.signUpNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.SIGNUP,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) }
    ) {
        SignUpScreen(modifier = modifier.padding(padding), navController = navController)
    }
}

fun NavController.navigateStartLamp(navOptions: NavOptions? = null) {
    this.navigate(Route.START_LAMP, navOptions)
}

fun NavGraphBuilder.startLampNavGraph(
    navController: NavController
) {
    composable(Route.START_LAMP) {
        StartLampScreen(navController = navController)
    }
}
