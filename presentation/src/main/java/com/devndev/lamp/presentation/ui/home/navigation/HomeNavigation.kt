package com.devndev.lamp.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.findlamp.FindLampScreen
import com.devndev.lamp.presentation.ui.home.main.HomeScreen

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(Route.HOME, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    isBarVisible: Boolean,
    onBarVisibleChange: (Boolean) -> Unit
) {
    composable(Route.HOME) {
        HomeScreen(modifier = modifier.padding(padding), navController = navController, isBarVisible = isBarVisible, onBarVisibleChange = onBarVisibleChange)
    }
}

fun NavController.navigateFind(
    navOptions: NavOptions? = null
) {
    this.navigate(Route.FIND, navOptions)
}

fun NavGraphBuilder.findNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.FIND
    ) {
        FindLampScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
