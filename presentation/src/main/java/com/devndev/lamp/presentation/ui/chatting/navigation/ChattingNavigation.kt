package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devndev.lamp.presentation.ui.chatting.ChatListScreen
import com.devndev.lamp.presentation.ui.chatting.ChatScreen
import com.devndev.lamp.presentation.ui.common.Route
import com.kakao.sdk.common.KakaoSdk.type

fun NavController.navigateChatList(navOptions: NavOptions? = null, chatRoomId: Int? = null) {
    val route = if (chatRoomId != null) {
        "chatList?chatRoomId=$chatRoomId"
    } else {
        "chatList"
    }
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.chatListNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = Route.CHAT_LIST_WITH_ID,
        arguments = listOf(
            navArgument("chatRoomId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    ) { backStackEntry ->
        val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toIntOrNull()

        ChatListScreen(
            modifier = modifier.padding(padding),
            navController = navController,
            initialChatRoomId = chatRoomId
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
