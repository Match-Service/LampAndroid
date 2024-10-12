package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor
import java.util.Locale

@Composable
fun LampTextField(
    width: Int,
    isGradient: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    maxLength: Int = 100,
    hintText: String,
    timerSeconds: Int = 0,
    isSearchMode: Boolean = false,
    onSearchKeyEvent: () -> Unit = {}
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(WomanColor, ManColor)
    )
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .then(
                if (width == 0) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.width(width.dp)
                }
            )
            .background(LampBlack, shape = RoundedCornerShape(27.dp))
            .then(
                if (isGradient) {
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
            value = query,
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    onQueryChange(newText)
                }
            },
            textStyle = Typography.medium18.copy(color = Color.White),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (isSearchMode) ImeAction.Search else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchKeyEvent()
                keyboardController?.hide()
            })

        ) { innerTextField ->
            if (query.isEmpty()) {
                Text(
                    text = hintText,
                    color = LightGray,
                    style = Typography.medium18
                )
            }
            innerTextField()
        }
        if (timerSeconds == 0) {
            IconButton(
                modifier = Modifier.size(10.dp),
                onClick = {
                    onQueryChange("")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.x_button),
                    contentDescription = "검색 지우기",
                    tint = LightGray
                )
            }
        } else {
            CountdownTimer(initialSeconds = timerSeconds)
        }
    }
}

@Composable
fun LampBigTextField(
    width: Int,
    height: Int,
    query: String,
    onQueryChange: (String) -> Unit,
    maxLength: Int = 100,
    hintText: String
) {
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .background(LampBlack, shape = RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(27.dp)
            )
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    onQueryChange(newText)
                }
            },
            textStyle = Typography.normal14.copy(color = Color.White),
            cursorBrush = SolidColor(Color.White),
            modifier = Modifier

        ) { innerTextField ->
            if (query.isEmpty()) {
                Text(
                    text = hintText,
                    color = LightGray,
                    style = Typography.normal14
                )
            }
            innerTextField()
        }
    }
}

@Composable
fun CountdownTimer(
    initialSeconds: Int
) {
    val minutes = initialSeconds / 60
    val seconds = initialSeconds % 60

    Text(
        text = String.format(Locale.US, "%02d:%02d", minutes, seconds),
        style = Typography.normal12,
        color = WomanColor
    )
}
