package com.devndev.lamp.presentation.ui.alarm.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.alarm.AlarmScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateAlarm(navOptions: NavOptions? = null) {
    this.navigate(Route.ALARM, navOptions)
}

fun NavGraphBuilder.alarmNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.ALARM,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        AlarmScreen(
            modifier = modifier.padding(padding),
            navController = navController
        )
    }
}
