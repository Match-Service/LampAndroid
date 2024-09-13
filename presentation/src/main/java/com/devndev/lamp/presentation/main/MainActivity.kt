package com.devndev.lamp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.ui.login.LoginViewModel
import com.devndev.lamp.presentation.ui.theme.LampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LampTheme {
                val loginViewModel: LoginViewModel = hiltViewModel()
                Lamp(viewModel = loginViewModel)
            }
        }
    }
}

@Composable
fun Lamp(viewModel: LoginViewModel) {
    val navController = rememberNavController()
    MainScreen(modifier = Modifier)
}
