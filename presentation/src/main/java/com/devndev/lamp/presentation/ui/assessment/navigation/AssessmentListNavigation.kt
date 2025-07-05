package com.devndev.lamp.presentation.ui.assessment.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.assessment.AssessmentListScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateAssessmentList(navOptions: NavOptions? = null) {
    this.navigate(Route.ASSESSMENT_LIST, navOptions)
}

fun NavGraphBuilder.assessmentListNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.ASSESSMENT_LIST,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        AssessmentListScreen(
            modifier = modifier.padding(padding),
            navController = navController
        )
    }
}