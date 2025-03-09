package com.devndev.lamp.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.HomeScreen
import com.devndev.lamp.presentation.ui.home.MatchingVoteScreen

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(Route.HOME, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.HOME) {
        HomeScreen(modifier = modifier.padding(padding), navController = navController)
    }
}

fun NavController.navigateVote(navOptions: NavOptions? = null) {
    this.navigate(Route.VOTE, navOptions)
}

fun NavGraphBuilder.voteNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.VOTE
    ) {
        MatchingVoteScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
