package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.chatting.ChattingScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateChatting(navOptions: NavOptions? = null) {
    this.navigate(Route.CHATTING, navOptions)
}

fun NavGraphBuilder.chattingNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    composable(Route.CHATTING) {
        ChattingScreen(modifier = modifier.padding(padding))
    }
}
