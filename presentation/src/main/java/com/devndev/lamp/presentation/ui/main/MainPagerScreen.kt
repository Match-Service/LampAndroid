package com.devndev.lamp.presentation.ui.main

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.devndev.lamp.presentation.ui.chatting.ChattingScreen
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.home.main.HomeScreen
import com.devndev.lamp.presentation.ui.mypage.MyPageScreen

@Composable
fun MainPagerScreen(
    pagerState: PagerState,
    modifier: Modifier,
    navController: NavController,
    initialPage: Int,
    signOut: () -> Unit
) {
    LaunchedEffect(initialPage) {
        pagerState.scrollToPage(initialPage)
    }
    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            MainScreenPage.HOME -> {
                HomeScreen(modifier = modifier, navController = navController)
            }

            MainScreenPage.CHATTING -> {
                ChattingScreen(modifier = modifier, navController = navController)
            }

            MainScreenPage.MY_PAGE -> {
                MyPageScreen(
                    modifier = modifier,
                    navController = navController,
                    signOut = signOut
                )
            }
        }
    }
}
