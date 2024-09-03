package com.devndev.lamp.presentation.ui.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.login.LoginScreen

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    this.navigate(Route.LOGIN, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.LOGIN) {
        LoginScreen()
    }
}
