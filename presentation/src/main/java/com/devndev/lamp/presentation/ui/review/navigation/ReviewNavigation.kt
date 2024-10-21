package com.devndev.lamp.presentation.ui.review.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.review.ReviewScreen

fun NavController.navigateReview(navOptions: NavOptions? = null) {
    this.navigate(Route.REVIEW, navOptions)
}

fun NavGraphBuilder.reviewNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.REVIEW) {
        ReviewScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
