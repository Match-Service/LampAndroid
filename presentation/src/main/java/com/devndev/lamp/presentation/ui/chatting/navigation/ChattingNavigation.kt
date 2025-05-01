package com.devndev.lamp.presentation.ui.chatting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300,
                    easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) { backStackEntry ->
        val chatRoomId = backStackEntry.arguments?.getString("chatRoomId")?.toIntOrNull() ?: 0
        ChatScreen(
            modifier = modifier.padding(),
            chatRoomId = chatRoomId,
            navController = navController
        )
    }
}
