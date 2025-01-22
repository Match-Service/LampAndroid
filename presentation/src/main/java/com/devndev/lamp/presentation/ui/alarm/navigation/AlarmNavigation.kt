package com.devndev.lamp.presentation.ui.alarm.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devndev.lamp.presentation.ui.alarm.AlarmScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateAlarm(isFromMain: Boolean, navOptions: NavOptions? = null) {
    val routeWithArgs = "${Route.ALARM}?isFromMain=$isFromMain"
    this.navigate(routeWithArgs, navOptions)
}

fun NavGraphBuilder.alarmNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    pagerState: PagerState
) {
    composable(
        "${Route.ALARM}?isFromMain={isFromMain}",
        arguments = listOf(
            navArgument("isFromMain") { type = NavType.BoolType }
        ),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val isFromMain = backStackEntry.arguments?.getBoolean("isFromMain")
        AlarmScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            pagerState = pagerState,
            isFromMain = isFromMain ?: true
        )
    }
}
