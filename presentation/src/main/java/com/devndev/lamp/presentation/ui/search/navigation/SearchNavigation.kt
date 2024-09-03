package com.devndev.lamp.presentation.ui.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.search.SearchScreen

fun NavController.navigateSearch(navOptions: NavOptions? = null) {
    this.navigate(Route.SEARCH, navOptions)
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(Route.SEARCH) {
        SearchScreen(modifier = modifier.padding(padding), navController = navController)
    }
}
