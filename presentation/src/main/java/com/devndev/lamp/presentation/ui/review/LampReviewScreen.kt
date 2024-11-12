package com.devndev.lamp.presentation.ui.review

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@SuppressLint("SuspiciousIndentation")
@Composable
fun LampReviewScreen() {
    // 현재 프로그레스 상태를 저장할 상태 변수
    var progress by remember { mutableStateOf(25f) }
    val tmpLampName = "이쁜2들"
    val tmpLampDate = "7월 6일"

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // circle animation
            BreathingCircleAnimation(progress)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(73.dp))
                Text(
                    text = "$tmpLampName ${stringResource(id = R.string.review_name)}",
                    color = Color.White,
                    style = Typography.semiBold25,
                    lineHeight = 33.sp,
                    textAlign = TextAlign.Center
                )
                // 리뷰 slider
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "$tmpLampDate, 즐거웠던 만큼 밝기를 올려주세요",
                    style = Typography.medium12,
                    color = Color.White
                )
                Column(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    ReviewProgressBar(progress) { newValue ->
                        progress = newValue
                    }
                    Spacer(modifier = Modifier.height(73.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewProgressBar(progress: Float, onProgressChange: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                    onProgressChange(
                        when {
                            newValue < 38 -> 25f
                            newValue < 63 -> 50f
                            newValue < 88 -> 75f
                            else -> 100f
                        }
                    )
                },
                valueRange = 25f..100f,
                steps = 0,
                modifier = Modifier
                    .fillMaxWidth(),
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
            horizontalArrangement = Arrangement.Center
        ) {
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
        }
    }
}

@Composable
fun BreathingCircleAnimation(progress: Float) {
    val animatedBlur by animateFloatAsState(
        targetValue = when {
            progress == 25f -> 50f
            progress == 100f -> 300f
            else -> {
                50f + (progress - 25f) * (300f - 50f) / (100f - 25f)
            }
        },
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearEasing
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Canvas(
            modifier = Modifier
                .size(5.dp)
                .background(Color.Transparent)
                .align(Alignment.Center)
        ) {
            drawBreathingCircle(
                blur = animatedBlur,
                alpha = 1f,
                center = center
            )
        }
    }
}

fun DrawScope.drawBreathingCircle(blur: Float, alpha: Float, center: Offset) {
    val radius = size.minDimension / 2

    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            color = Color.Transparent.toArgb()
            setShadowLayer(
                blur,
                0f,
                0f,
                WomanColor.copy(alpha = alpha + 0.5f).toArgb()
            )
        }
        canvas.nativeCanvas.drawCircle(center.x, center.y, radius + blur, paint)
    }

    drawCircle(
        color = WomanColor.copy(alpha = alpha),
        radius = radius,
        center = center
    )
}

@Preview
@Composable
fun B() {
    LampReviewScreen()
}
