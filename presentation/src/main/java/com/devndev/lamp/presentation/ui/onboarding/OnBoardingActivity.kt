package com.devndev.lamp.presentation.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.devndev.lamp.presentation.theme.LampTheme
import com.devndev.lamp.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LampTheme {
                OnBoardingScreen(
                    onButtonClick = {
                        MainActivity.openActivity(this)
                        finish()
                    }
                )
            }
        }
    }

    companion object {
        fun openActivity(context: Activity) {
            context.startActivity(
                Intent(context, OnBoardingActivity::class.java)
            )
        }
    }
}
