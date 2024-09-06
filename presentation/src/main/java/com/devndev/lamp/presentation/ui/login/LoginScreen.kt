package com.devndev.lamp.presentation.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val signInLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        viewModel.signInWithGoogle(result.data)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LampBlack)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.2f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = context.getString(R.string.login_header),
                color = Color.White,
                style = Typography.medium18
            )
            Icon(
                painter = painterResource(id = R.drawable.main_lamp_logo),
                contentDescription = "loginLampLogo",
                tint = Color.Unspecified
            )
        }
        val buttonColor = ButtonDefaults.buttonColors(
            containerColor = Gray,
            contentColor = Color.White
        )
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.25f))
        Column(
            modifier = Modifier.width(270.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {
                    val signInIntent = viewModel.getSignInIntent()
                    signInLauncher.launch(signInIntent)
                },
                colors = buttonColor
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "google_logo"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = context.getString(R.string.sign_in_google),
                    style = Typography.medium18
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = {},
                colors = buttonColor
            ) {
                Text(text = context.getString(R.string.sign_in_email), style = Typography.medium18)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = context.getString(R.string.have_account),
                    color = Color.White,
                    style = Typography.normal12
                )
                Text(
                    text = buildAnnotatedString {
                        append(context.getString(R.string.login))
                        addStyle(
                            style = SpanStyle(textDecoration = TextDecoration.Underline),
                            start = 0,
                            end = this.length
                        )
                    },
                    color = Color.White,
                    style = Typography.normal12
                )
            }
        }
    }
}
