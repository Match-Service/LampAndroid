package com.devndev.lamp.presentation.ui.home.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.HomeScreen

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(Route.HOME, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.HOME) {
        HomeScreen(modifier = modifier.padding(padding))
    }
}
