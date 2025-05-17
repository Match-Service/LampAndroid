package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devndev.lamp.presentation.ui.alarm.navigation.alarmNavGraph

@Composable
fun ChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    chatRoomId: Int
) {
    val pagerState = rememberPagerState(initialPage = 2, pageCount = { 1 })
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        chatNavGraph(
            padding = PaddingValues(),
            navController = navController,
            chatRoomId = chatRoomId
        )
        alarmNavGraph(
            padding = PaddingValues(),
            navController = navController,
            pagerState = pagerState
        )
    }
}
