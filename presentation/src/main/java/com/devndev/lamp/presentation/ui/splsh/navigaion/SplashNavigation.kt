package com.devndev.lamp.presentation.ui.splsh.navigaion

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateSplash(navOptions: NavOptions? = null) {
    this.navigate(Route.SPLASH, navOptions)
}

fun NavGraphBuilder.splashNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.SPLASH) {
//        var showSplash by remember { mutableStateOf(true) }
//        SplashScreen {
//            showSplash = false // 애니메이션이 끝나면 메인 화면을 표시
//        }
    }
}
