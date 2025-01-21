package com.devndev.lamp.presentation.ui.login.navigation

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devndev.lamp.domain.model.signup.User
import com.devndev.lamp.presentation.ui.registration.navigation.registrationNavGraph

@Composable
fun LoginNavHost(
    onClickSignInButton: (Intent) -> Unit,
    onClickStartButton: (user: User, bitmaps: List<Bitmap?>) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        loginNavGraph(
            navController = navController,
            onClickSignInButton = onClickSignInButton
        )
        registrationNavGraph(
            navController = navController,
            onClickStartButton = onClickStartButton
        )
    }
}
