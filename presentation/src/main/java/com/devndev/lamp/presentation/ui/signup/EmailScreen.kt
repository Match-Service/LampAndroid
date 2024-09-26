package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun EmailScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    certificationNumber: String,
    onCertificationNumberChange: (String) -> Unit,
    isValidEmail: Boolean,
    isValidCertificationNumber: Boolean,
    isGetNumberButtonClick: Boolean,
    isCertifyButtonClick: Boolean
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
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .background(LampBlack, shape = RoundedCornerShape(27.dp))
                    .border(
                        width = 1.dp,
                        color = LightGray,
                        shape = RoundedCornerShape(27.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = emailQuery,
                    onValueChange = { newText ->
                        emailQuery = newText
                        onEmailChange(newText)
                    },
                    textStyle = Typography.medium18.copy(color = Color.White),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                ) { innerTextField ->
                    if (emailQuery.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_email),
                            color = LightGray,
                            style = Typography.medium18
                        )
                    }
                    innerTextField()
                }
                IconButton(
                    modifier = Modifier.size(10.dp),
                    onClick = {
                        emailQuery = ""
                        onEmailChange(emailQuery)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_button),
                        contentDescription = "검색 지우기",
                        tint = LightGray
                    )
                }
            }

            if (!isValidEmail && isGetNumberButtonClick) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.invalid_email),
                    color = WomanColor,
                    style = Typography.normal12
                )
            }

            if (isGetNumberButtonClick && isValidEmail) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .width(300.dp)
                        .background(LampBlack, shape = RoundedCornerShape(27.dp))
                        .then(
                            if (!isValidCertificationNumber && isCertifyButtonClick) {
                                Modifier.drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    drawRoundRect(
                                        brush = gradientBrush,
                                        size = size,
                                        cornerRadius = CornerRadius(27.dp.toPx(), 27.dp.toPx()),
                                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                                            strokeWidth
                                        )
                                    )
                                }
                            } else {
                                Modifier.border(
                                    width = 1.dp,
                                    color = LightGray,
                                    shape = RoundedCornerShape(27.dp)
                                )
                            }
                        )
                        .padding(horizontal = 20.dp, vertical = 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = numberQuery,
                        onValueChange = { newText ->
                            numberQuery = newText
                            onCertificationNumberChange(newText)
                        },
                        textStyle = Typography.medium18.copy(color = Color.White),
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                    ) { innerTextField ->
                        if (numberQuery.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.certification_number),
                                color = LightGray,
                                style = Typography.medium18
                            )
                        }
                        innerTextField()
                    }
                    CountdownTimer()
                }
            }
        }
    }
}

@Composable
fun CountdownTimer(startMinutes: Int = 3, startSeconds: Int = 0) {
    var totalSeconds by remember { mutableIntStateOf(startMinutes * 60 + startSeconds) }

    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning && totalSeconds > 0) {
            delay(1000L)
            totalSeconds--
        }
        if (totalSeconds == 0) {
            isRunning = false
        }
    }

    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    Text(
        text = String.format(Locale.US, "%02d:%02d", minutes, seconds),
        style = Typography.normal12,
        color = WomanColor
    )
}
