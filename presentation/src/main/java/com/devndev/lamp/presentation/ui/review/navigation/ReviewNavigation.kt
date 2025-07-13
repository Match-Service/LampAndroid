package com.devndev.lamp.presentation.ui.review.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.devndev.lamp.presentation.ui.review.ReviewScreen

fun NavController.navigateReview(
    navOptions: NavOptions? = null,
    lampMatchId: Int
) {
    val route = "review/$lampMatchId"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.reviewNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.REVIEW,
        arguments = listOf(
            navArgument("lampMatchId") { type = NavType.IntType }
        ),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val lampMatchId = backStackEntry.arguments?.getInt("lampMatchId") ?: 0
        ReviewScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            lampMatchId = lampMatchId
        )
    }
}
