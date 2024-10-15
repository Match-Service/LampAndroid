package com.devndev.lamp.presentation.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.devndev.lamp.presentation.main.MainPagerScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateMain(page: Int, navOptions: NavOptions? = null) {
    this.navigate("${Route.MAIN}/$page", navOptions)
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    navController: NavController
) {
    navigation(startDestination = "${Route.MAIN}/0", route = Route.MAIN) {
        composable(route = "${Route.MAIN}/{page}") { backStackEntry ->
            val page = backStackEntry.arguments?.getString("page")?.toIntOrNull() ?: 0
            MainPagerScreen(pagerState, modifier.padding(padding), navController, initialPage = page)
        }
    }
}
