package com.devndev.lamp.presentation.ui.notification.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.notification.NotificationScreen

fun NavController.navigateNotification(navOptions: NavOptions? = null) {
    this.navigate(Route.NOTIFICATION, navOptions)
}

fun NavGraphBuilder.notificationNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.NOTIFICATION,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        NotificationScreen(modifier = modifier.padding(padding), navController)
    }
}
