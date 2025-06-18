package com.devndev.lamp.presentation.ui.creation.navigation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.creation.LampCreationScreen

fun NavController.navigateCreation(
    navOptions: NavOptions? = null,
    isEdit: Boolean
) {
    val route = "creation/$isEdit"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.creationNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.CREATION,
        arguments = listOf(
            navArgument("isEdit") { type = NavType.BoolType }
        ),
        enterTransition = { slideInVertically(initialOffsetY = { it }) },
        exitTransition = { slideOutVertically(targetOffsetY = { it }) }
    ) { backStackEntry ->
        val isEdit = backStackEntry.arguments?.getBoolean("isEdit") ?: false
        LampCreationScreen(
            isEdit = isEdit,
            modifier = modifier.padding(padding),
            navController = navController
        )
    }
}
