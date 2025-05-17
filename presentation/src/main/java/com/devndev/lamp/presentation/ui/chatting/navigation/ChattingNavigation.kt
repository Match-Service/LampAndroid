package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.chatting.ChatListScreen
import com.devndev.lamp.presentation.ui.chatting.ChatScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateChatList(navOptions: NavOptions? = null) {
    this.navigate(Route.CHAT_LIST, navOptions)
}

fun NavGraphBuilder.chatListNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.CHAT_LIST
    ) {
        ChatListScreen(
            modifier = modifier.padding(padding),
            navController = navController
        )
    }
}

fun NavGraphBuilder.chatNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController,
    chatRoomId: Int
) {
    composable(
        route = Route.CHAT
    ) {
        ChatScreen(
            modifier = modifier.padding(),
            chatRoomId = chatRoomId,
            navController = navController
        )
    }
}
