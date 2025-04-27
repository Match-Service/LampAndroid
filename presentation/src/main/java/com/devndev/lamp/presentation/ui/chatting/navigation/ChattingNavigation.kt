package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.devndev.lamp.presentation.ui.chatting.ChatScreen
import com.devndev.lamp.presentation.ui.common.Route

fun NavController.navigateChat(chatRoomId: Int, navOptions: NavOptions? = null) {
    this.navigate("${Route.CHAT}/$chatRoomId", navOptions)
}

fun NavGraphBuilder.chatNavGraph(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable(
        route = "${Route.CHAT}/{chatRoomId}",
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) { backStackEntry ->
        val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toIntOrNull() ?: 0
        ChatScreen(
            modifier = modifier.padding(padding),
            chatRoomId = chatRoomId,
            navController = navController
        )
    }
}
