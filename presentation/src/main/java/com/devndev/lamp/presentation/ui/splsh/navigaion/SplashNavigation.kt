package com.devndev.lamp.presentation.ui.splsh.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.splsh.SplashScreen

fun NavController.navigateSplash(navOptions: NavOptions? = null) {
    this.navigate(Route.SPLASH, navOptions)
}

fun NavGraphBuilder.splashNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.SPLASH) {
        SplashScreen()
    }
}
