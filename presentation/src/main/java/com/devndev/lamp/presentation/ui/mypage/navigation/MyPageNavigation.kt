package com.devndev.lamp.presentation.ui.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.mypage.MyPageScreen

fun NavController.navigateMyPage(navOptions: NavOptions? = null) {
    this.navigate(Route.MYPAGE, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.MYPAGE) {
        MyPageScreen(modifier = modifier.padding(padding))
    }
}
