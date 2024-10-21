package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.PasswordStatus
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun PasswordScreen(
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    passwordStatus: Int
) {
    var passwordQuery by remember { mutableStateOf(password) }
    var confirmPasswordQuery by remember { mutableStateOf(confirmPassword) }

    SelectionScreen(text = "") {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampTextField(
                width = 300,
                isGradient = passwordStatus == PasswordStatus.INVALID_PASSWORD,
                query = passwordQuery,
                onQueryChange = {
                    passwordQuery = it
                    onPasswordChange(it)
                },
                maxLength = 16,
                hintText = stringResource(id = R.string.input_password),
                isPasswordMode = true
            )

            when (passwordStatus) {
                PasswordStatus.NONE,
                PasswordStatus.INVALID_PASSWORD -> {
                    Spacer(modifier = Modifier.height(15.dp))
                    val color = if (passwordStatus == PasswordStatus.NONE) {
                        Color.White
                    } else {
                        WomanColor
                    }
                    Text(
                        text = stringResource(id = R.string.guide_input_password),
                        style = Typography.normal12,
                        color = color
                    )
                }

                PasswordStatus.CONFIRM_PASSWORD,
                PasswordStatus.SUCCESS -> {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = stringResource(id = R.string.valid_password),
                        style = Typography.normal12,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    LampTextField(
                        width = 300,
                        isGradient = false,
                        query = confirmPasswordQuery,
                        onQueryChange = {
                            confirmPasswordQuery = it
                            onConfirmPasswordChange(it)
                        },
                        maxLength = 16,
                        hintText = stringResource(id = R.string.input_confirm_password),
                        isPasswordMode = true
                    )
                    if (passwordStatus == PasswordStatus.SUCCESS) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(
                            text = stringResource(id = R.string.valid_password),
                            style = Typography.normal12,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
