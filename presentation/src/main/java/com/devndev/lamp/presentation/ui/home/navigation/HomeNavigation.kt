package com.devndev.lamp.presentation.ui.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.main.HomeScreen
import com.devndev.lamp.presentation.ui.home.matchinghome.MatchingHomeScreen
import kotlinx.coroutines.flow.SharedFlow

fun NavController.navigateHome(navOptions: NavOptions? = null) {
    this.navigate(Route.HOME, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    isAssessmentExist: (Boolean) -> Unit,
    isTopBarVisible: Boolean,
    isBottomBarVisible: Boolean,
    onTopBarVisibleChange: (Boolean) -> Unit,
    onBottomBarVisibleChange: (Boolean) -> Unit
) {
    composable(Route.HOME) {
        HomeScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            isAssessmentExist = {
                isAssessmentExist(it)
            },
            onTopBarVisibleChange = onTopBarVisibleChange,
            onBottomBarVisibleChange = onBottomBarVisibleChange
        )
    }
}

fun NavController.navigateMatching(
    navOptions: NavOptions? = null
) {
    this.navigate(Route.MATCHING, navOptions)
}

fun NavGraphBuilder.matchingNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    updateEvent: SharedFlow<Unit>,
    updateStatus: SharedFlow<String>
) {
    composable(
        Route.FIND
    ) {
        MatchingHomeScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            updateEvent = updateEvent,
            updateStatus = updateStatus
        )
    }
}
