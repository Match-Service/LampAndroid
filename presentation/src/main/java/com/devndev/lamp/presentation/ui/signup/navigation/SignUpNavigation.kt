package com.devndev.lamp.presentation.ui.signup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.signup.SignUpScreen

fun NavController.navigateSignUp(navOptions: NavOptions? = null) {
    this.navigate(Route.SIGNUP, navOptions)
}

fun NavGraphBuilder.signUpNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.SIGNUP) {
        SignUpScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
