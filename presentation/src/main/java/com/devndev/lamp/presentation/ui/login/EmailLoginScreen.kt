package com.devndev.lamp.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.EmailLoginStatus
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.login.navigation.navigateForgotPassword

@Composable
fun EmailLoginScreen(modifier: Modifier, navController: NavController) {
    var emailQuery by remember { mutableStateOf("") }
    var passwordQuery by remember { mutableStateOf("") }
    var emailLoginStatus by remember { mutableIntStateOf(EmailLoginStatus.NONE) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TopNavigationBar(
                text = stringResource(id = R.string.login),
                onBackButtonClick = { navController.popBackStack() },
                onXButtonClick = { navController.popBackStack() }
            )
            SelectionScreen(text = "") {
                LampTextField(
                    width = 300,
                    query = emailQuery,
                    onQueryChange = {
                        emailQuery = it
                    },
                    hintText = stringResource(id = R.string.input_email)
                )
                if (emailLoginStatus == EmailLoginStatus.INVALID_EMAIL) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.invalid_email_login),
                        color = WomanColor,
                        style = Typography.normal12
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                LampTextField(
                    width = 300,
                    query = passwordQuery,
                    onQueryChange = {
                        passwordQuery = it
                    },
                    maxLength = 16,
                    hintText = stringResource(id = R.string.input_password),
                    isPasswordMode = true
                )
                if (emailLoginStatus == EmailLoginStatus.INVALID_PASSWORD) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.invalid_password_login),
                        color = WomanColor,
                        style = Typography.normal12
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.clickable {
                    val navOption = navOptions {
                        launchSingleTop = true
                    }
                    navController.navigateForgotPassword(navOption)
                },
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.forgot_password))
                    addStyle(
                        style = SpanStyle(textDecoration = TextDecoration.Underline),
                        start = 0,
                        end = this.length
                    )
                },
                color = Color.White,
                style = Typography.normal15
            )
            LampButton(
                isGradient = true,
                buttonText = stringResource(id = R.string.login),
                onClick = { emailLoginStatus = (0..2).random() },
                enabled = emailQuery.isNotEmpty() && passwordQuery.isNotEmpty()
            )
        }
    }
}
