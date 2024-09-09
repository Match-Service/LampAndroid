package com.devndev.lamp.presentation.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun InstagramScreen(
    instagramID: String,
    onInstagramIDChange: (String) -> Unit,
    isValid: Boolean,
    isAuthButtonClicked: Boolean
) {
    val context = LocalContext.current

    SelectionScreen(text = context.getString(R.string.input_insta)) {
        var nameQuery by remember { mutableStateOf(instagramID) }
        Spacer(modifier = Modifier.height(24.dp))

        val gradientBrush = Brush.linearGradient(
            colors = listOf(WomanColor, ManColor)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .background(LampBlack, shape = RoundedCornerShape(27.dp))
                    .then(
                        if (!isValid && isAuthButtonClicked) {
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
                        } else if (isValid) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(27.dp)
                            )
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
                    value = nameQuery,
                    onValueChange = { newText ->
                        nameQuery = newText
                        onInstagramIDChange(newText)
                    },
                    textStyle = Typography.medium18.copy(color = Color.White),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                ) { innerTextField ->
                    if (nameQuery.isEmpty()) {
                        Text(
                            text = context.getString(R.string.guide_insta),
                            color = LightGray,
                            style = Typography.medium18
                        )
                    }
                    innerTextField()
                }
                IconButton(
                    modifier = Modifier.size(10.dp),
                    onClick = {
                        nameQuery = ""
                        onInstagramIDChange(nameQuery)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_button),
                        contentDescription = "검색 지우기",
                        tint = LightGray
                    )
                }
            }
            var guideText: String
            var guideTextColor = Color.White
            if (isAuthButtonClicked) {
                if (isValid) {
                    guideText = context.getString(R.string.auth_success)
                } else {
                    guideText = context.getString(R.string.auth_failure)
                    guideTextColor = WomanColor
                }
            } else {
                guideText = context.getString(R.string.university_guide1)
            }
            Text(
                modifier = Modifier.width(230.dp),
                text = guideText,
                style = Typography.normal12,
                color = guideTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
