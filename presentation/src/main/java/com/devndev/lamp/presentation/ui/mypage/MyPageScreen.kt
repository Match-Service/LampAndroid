package com.devndev.lamp.presentation.ui.mypage

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.presentation.R
import kotlin.system.exitProcess

@Composable
fun MyPageScreen(modifier: Modifier, viewModel: MyPageViewModel = hiltViewModel()) {
    val logTag = "MyPageScreen"
    val context = LocalContext.current
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce = remember { false }

    BackHandler {
        Log.d(logTag, "back button clicked")
        if (backPressedOnce) {
            Log.d(logTag, "back button clicked 2 times end process")
            exitProcess(0)
        } else {
            Log.d(logTag, "back button clicked 1 times show Toast")
            Toast.makeText(
                context,
                context.getString(R.string.back_button_end_message),
                Toast.LENGTH_SHORT
            ).show()
            backPressedOnce = true
            handler.postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }
    Box(modifier = modifier) {
        Button(onClick = { viewModel.signOut() }) {
            Text("임시 로그아웃")
        }
    }
}
