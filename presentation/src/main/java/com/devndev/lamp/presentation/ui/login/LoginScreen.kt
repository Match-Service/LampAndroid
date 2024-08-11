package com.devndev.lamp.presentation.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.presentation.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), modifier: Modifier) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login Screen",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val kakaoLogin = {
            viewModel.login { success ->
                if (success) {
                    Log.d("LoginScreen", "Kakao login success")
                } else {
                    Log.d("LoginScreen", "Kakao login fail")
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.kakao_login_medium_narrow),
            contentDescription = "Kakao Login",
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)
                .clickable {
                    coroutineScope.launch {
                        kakaoLogin.invoke()
                    }
                }
        )
    }
}
