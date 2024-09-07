package com.devndev.lamp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.main.navigation.mainNavGraph
import com.devndev.lamp.presentation.main.navigation.navigateMain
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.login.navigation.loginNavGraph
import com.devndev.lamp.presentation.ui.login.navigation.navigateLogin
import com.devndev.lamp.presentation.ui.signup.navigation.signupNavGraph
import com.devndev.lamp.presentation.ui.splsh.navigaion.splashNavGraph
import com.devndev.lamp.presentation.ui.theme.LampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LampTheme {
                Lamp()
            }
        }
    }
}

@Composable
fun Lamp(viewModel: LoginViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkLoginStatus()
    }

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            if (isLoggedIn) {
                navController.navigateMain()
            } else {
                navController.navigateLogin()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoading) Route.SPLASH else if (isLoggedIn) Route.MAIN else Route.LOGIN
    ) {
        splashNavGraph(padding = PaddingValues())
        loginNavGraph(padding = PaddingValues(), navController = navController)
        mainNavGraph(padding = PaddingValues())
        signupNavGraph(padding = PaddingValues(), navController = navController)
    }
}
