package com.devndev.lamp.presentation.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.main.MainScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateMain(navOptions: NavOptions? = null) {
    this.navigate(Route.MAIN, navOptions)
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.MAIN) {
        MainScreen(modifier = modifier)
    }
}
