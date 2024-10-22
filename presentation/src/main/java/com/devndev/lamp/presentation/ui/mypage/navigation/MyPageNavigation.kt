package com.devndev.lamp.presentation.ui.mypage.navigation

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
import com.devndev.lamp.presentation.ui.mypage.MyPageScreen
import com.devndev.lamp.presentation.ui.mypage.ProfileEditScreen

fun NavController.navigateMyPage(navOptions: NavOptions? = null) {
    this.navigate(Route.MYPAGE, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.MYPAGE) {
        MyPageScreen(modifier = modifier.padding(padding), navController = navController)
    }
}

fun NavController.navigateProfileEdit(navOptions: NavOptions? = null) {
    this.navigate(Route.PROFILE_EDIT, navOptions)
}

fun NavGraphBuilder.profileEditNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        Route.PROFILE_EDIT,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        ProfileEditScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
