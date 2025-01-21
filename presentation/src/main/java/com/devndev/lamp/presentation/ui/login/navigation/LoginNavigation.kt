package com.devndev.lamp.presentation.ui.login.navigation

import android.content.Intent
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
import com.devndev.lamp.presentation.ui.login.EmailLoginScreen
import com.devndev.lamp.presentation.ui.login.LoginScreen

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    this.navigate(Route.LOGIN, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    navController: NavController,
    onClickSignInButton: (Intent) -> Unit
) {
    composable(Route.LOGIN) {
        LoginScreen(
            navController,
            onClickSignInButton = onClickSignInButton
        )
    }
}

fun NavController.navigateEmailLogin(navOptions: NavOptions? = null) {
    this.navigate(Route.EMAIL_LOGIN, navOptions)
}

fun NavGraphBuilder.emailLoginNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.EMAIL_LOGIN,
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) }
    ) {
        EmailLoginScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
