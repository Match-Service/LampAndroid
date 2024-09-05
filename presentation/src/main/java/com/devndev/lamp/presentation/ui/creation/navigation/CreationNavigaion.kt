package com.devndev.lamp.presentation.ui.creation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.LampCreationScreen

fun NavController.navigateCreation(navOptions: NavOptions? = null) {
    this.navigate(Route.CREATION, navOptions)
}

fun NavGraphBuilder.creationNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.CREATION) {
        LampCreationScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
