package com.devndev.lamp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
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
                val loginViewModel: LoginViewModel = hiltViewModel()
                Lamp(viewModel = loginViewModel)
            }
        }
    }
}

@Composable
fun Lamp(viewModel: LoginViewModel) {
    val navController = rememberNavController()
//    MainScreen(modifier = Modifier)
    var showSplash by remember { mutableStateOf(true) }
    if (showSplash) {
        SplashScreen {
            showSplash = false // 애니메이션이 끝나면 메인 화면을 표시
        }
    } else {
        // 애니메이션 후 메인 화면 표시
        MainScreen(modifier = Modifier)
    }
}
