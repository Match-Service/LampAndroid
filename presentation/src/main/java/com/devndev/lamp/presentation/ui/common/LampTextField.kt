package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.res.painterResource
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
    isGradient: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    maxLength: Int = 100,
    hintText: String,
    timerSeconds: Int = 0
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(WomanColor, ManColor)
    )
    Row(
        modifier = Modifier
            .width(300.dp)
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
            modifier = Modifier
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
