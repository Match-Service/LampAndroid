package com.devndev.lamp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
    val isLoggedIn = viewModel.isLoggedIn.collectAsState().value
    val isInitialized = viewModel.isInitialized.collectAsState().value
    Log.d("----", "isLoggedIn $isLoggedIn")
    if (isInitialized) {
        if (isLoggedIn) {
            MainScreen(modifier = Modifier)
        } else {
            LoginScreen(modifier = Modifier)
        }
    } else {
        SplashScreen()
    }
}
