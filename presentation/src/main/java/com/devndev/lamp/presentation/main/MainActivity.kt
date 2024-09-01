package com.devndev.lamp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.ui.login.LoginScreen
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.splsh.SplashScreen
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
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoading) "splash" else if (isLoggedIn) "main" else "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("main") {
            MainScreen(modifier = Modifier)
        }
        composable("splash") {
            SplashScreen()
        }
    }
}
