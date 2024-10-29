package com.devndev.lamp.presentation.ui.review

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun LampReviewScreen() {
    val tmpLampName = "이쁜2들"
    val tmpLampDate = "7월 6일"

    SelectionScreen(text = "$tmpLampName ${stringResource(id = R.string.review_name)}") {
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "$tmpLampDate, 즐거웠던 만큼 밝기를 올려주세요",
            style = Typography.medium12,
            color = Color.White
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, end = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReviewProgressBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewProgressBar() {
    // 현재 프로그레스 상태를 저장할 상태 변수
    var progress by remember { mutableStateOf(25f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Draw dividers
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.Center)
                    .zIndex(1f)
            ) {
                val width = size.width
                val dividerPositions = listOf(width / 3, (2 * width) / 3)

                dividerPositions.forEach { position ->
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x = position, y = 0f),
                        end = Offset(x = position, y = size.height),
                        strokeWidth = 1f
                    )
                }
            }

            // 슬라이더
            Slider(
                value = progress,
                onValueChange = { newValue ->
                    progress = when {
                        newValue < 38 -> 25f
                        newValue < 63 -> 50f
                        newValue < 88 -> 75f
                        else -> 100f
                    }
                },
                valueRange = 25f..100f,
                steps = 0,
                modifier = Modifier
                    .fillMaxSize(),
                thumb = {
                    Image(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Gray
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "25",
                color = Gray3,
                style = Typography.normal12
            )
            Text(
                text = when (progress) {
                    25f -> "아쉬웠어요"
                    50f -> "보통이에요"
                    75f -> "즐거웠어요"
                    100f -> "최고에요"
                    else -> ""
                },
                color = Color.White,
                style = Typography.medium15
            )
            Text(
                text = "100",
                color = Gray3,
                style = Typography.normal12
            )
        }
    }
}

@Preview
@Composable
fun B() {
    LampReviewScreen()
}
