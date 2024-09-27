package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.EmailStatus
import com.devndev.lamp.presentation.ui.common.LampTextField
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun EmailScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    certificationNumber: String,
    onCertificationNumberChange: (String) -> Unit,
    emailStatus: Int,
    totalSeconds: Int,
    onResendButtonClick: () -> Unit
) {
    var emailQuery by remember { mutableStateOf(email) }
    var numberQuery by remember { mutableStateOf(certificationNumber) }
    val gradientBrush = Brush.linearGradient(
        colors = listOf(WomanColor, ManColor)
    )

    SelectionScreen(text = "") {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LampTextField(
                isGradient = false,
                query = emailQuery,
                onQueryChange = {
                    emailQuery = it
                    onEmailChange(it)
                },
                hintText = stringResource(id = R.string.input_email)
            )
        }

        if (emailStatus == EmailStatus.INVALID_EMAIL) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.invalid_email),
                color = WomanColor,
                style = Typography.normal12
            )
        }
        if (emailStatus != EmailStatus.NONE && emailStatus != EmailStatus.INVALID_EMAIL) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                LampTextField(
                    isGradient = emailStatus != EmailStatus.NORMAL,
                    query = numberQuery,
                    onQueryChange = {
                        numberQuery = it
                        onCertificationNumberChange(it)
                    },
                    hintText = stringResource(id = R.string.certification_number),
                    timerSeconds = totalSeconds
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            EmailBottomArea(emailStatus = emailStatus, onResendClick = {
                onResendButtonClick()
            })
        }
    }
}

@Composable
fun EmailBottomArea(emailStatus: Int, onResendClick: () -> Unit) {
    when (emailStatus) {
        EmailStatus.NORMAL -> {
            Column(
                modifier = Modifier.width(225.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.send_email_guide),
                    color = Color.White,
                    style = Typography.normal12
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.not_send_email),
                        color = Color.White,
                        style = Typography.normal12
                    )
                    ResendText(onClick = { onResendClick() })
                }
            }
        }

        EmailStatus.TIME_OUT -> {
            Row(
                modifier = Modifier.width(260.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.timeout_certification_number),
                    color = WomanColor,
                    style = Typography.normal12
                )
                ResendText(onClick = { onResendClick() })
            }
        }

        EmailStatus.INVALID_CERTIFICATION_NUMBER -> {
            Row(
                modifier = Modifier.width(260.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.invalid_certification_number),
                    color = WomanColor,
                    style = Typography.normal12
                )
                ResendText(onClick = { onResendClick() })
            }
        }
    }
}

@Composable
fun ResendText(onClick: () -> Unit) {
    Text(
        modifier = Modifier.clickable {
            onClick()
        },
        text = buildAnnotatedString {
            append(stringResource(id = R.string.resend_certification_number))
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
