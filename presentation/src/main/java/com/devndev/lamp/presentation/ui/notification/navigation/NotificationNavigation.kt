package com.devndev.lamp.presentation.ui.notification.navigation

import android.os.Bundle
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
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.notification.NotificationScreen

fun NavController.navigateNotification(isFromMain: Boolean, navOptions: NavOptions? = null) {
    val routeWithArgs = "${Route.NOTIFICATION}?isFromMain=$isFromMain"
    this.navigate(routeWithArgs, navOptions)
}

fun NavGraphBuilder.notificationNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    pagerState: PagerState,
) {
    composable(
        "${Route.NOTIFICATION}?isFromMain={isFromMain}",
        arguments = listOf(
            navArgument("isFromMain") { type = NavType.BoolType },
        ),
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val isFromMain = backStackEntry.arguments?.getBoolean("isFromMain")
        NotificationScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            pagerState = pagerState,
            isFromMain = isFromMain ?: true
        )
    }
}
