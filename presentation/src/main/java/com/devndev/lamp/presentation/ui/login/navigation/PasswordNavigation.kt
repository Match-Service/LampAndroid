package com.devndev.lamp.presentation.ui.login.navigation

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
import com.devndev.lamp.presentation.ui.login.ForgotPasswordScreen

fun NavController.navigateForgotPassword(navOptions: NavOptions? = null) {
    this.navigate(Route.FORGOT_PASSWORD, navOptions)
}

fun NavGraphBuilder.forgotPasswordNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.FORGOT_PASSWORD,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) }
    ) {
        ForgotPasswordScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
