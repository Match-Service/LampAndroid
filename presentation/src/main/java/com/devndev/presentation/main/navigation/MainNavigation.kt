package com.devndev.presentation.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.presentation.main.MainScreen

fun NavController.navigateMain(navOptions: NavOptions? = null) {
    this.navigate(MainRoute.ROUTE, navOptions)
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(MainRoute.ROUTE) {
        MainScreen(
            modifier = modifier
        )
    }
}

object MainRoute {
    const val ROUTE = "main"
}
