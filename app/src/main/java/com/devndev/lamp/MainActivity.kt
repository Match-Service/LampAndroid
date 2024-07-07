package com.devndev.lamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.devndev.lamp.ui.theme.LampTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lamp {
                MainScreen()
            }
        }
    }
}

@Composable
fun Lamp(content: @Composable () -> Unit) {
    LampTheme {
        content()
    }
}
