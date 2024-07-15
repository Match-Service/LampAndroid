package com.devndev.presentation.ui.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.presentation.ui.login.LoginScreen

fun NavController.navigateLogin(navOptions: NavOptions? = null) {
    this.navigate(LoginRoute.ROUTE, navOptions)
}

fun NavGraphBuilder.loginNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(LoginRoute.ROUTE) {
        LoginScreen(
            modifier = modifier
        )
    }
}

object LoginRoute {
    const val ROUTE = "login"
}
