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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.devndev.lamp.domain.model.assessment.AssessmentDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.utils.DateFormatUtil

@SuppressLint("SuspiciousIndentation")
@Composable
fun LampReviewScreen(
    assessment: AssessmentDomainModel,
    lampScore: Int,
    gender: String,
    onScoreChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // circle animation
            BreathingCircleAnimation(convertIntToF(lampScore), gender)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(73.dp))
                Text(
                    text = "${assessment.lampName} ${stringResource(id = R.string.review_name)}",
                    color = Color.White,
                    style = Typography.semiBold25,
                    lineHeight = 33.sp,
                    textAlign = TextAlign.Center
                )
                // 리뷰 slider
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = "${DateFormatUtil.formatDateToMonthDay(assessment.meetingTime)}, 즐거웠던 만큼 밝기를 올려주세요",
                    style = Typography.medium12,
                    color = Color.White
                )
                Column(
                    modifier = Modifier.wrapContentSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    ReviewProgressBar(convertIntToF(lampScore)) { newValue ->
                        onScoreChange(convertFToInt(newValue))
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
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    .fillMaxWidth()
                    .zIndex(0f),
                thumb = {},
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Gray
                )
            )

            // 구분선 그리기
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

            // thumb(하트) 따로 그리기
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .offset {
                        val offsetX =
                            ((progress - 25f) / (100f - 25f) * (constraints.maxWidth - 24.dp.toPx())).toInt()
                        IntOffset(offsetX, 0)
                    }
                    .zIndex(3f)
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
fun BreathingCircleAnimation(progress: Float, gender: String) {
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
                center = center,
                gender = gender
            )
        }
    }
}

fun DrawScope.drawBreathingCircle(blur: Float, alpha: Float, center: Offset, gender: String) {
    val circleColor = if (gender == "MALE") {
        WomanColor
    } else {
        ManColor
    }

    val radius = size.minDimension / 2

    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            color = Color.Transparent.toArgb()
            setShadowLayer(
                blur,
                0f,
                0f,
                circleColor.copy(alpha = alpha + 0.5f).toArgb()
            )
        }
        canvas.nativeCanvas.drawCircle(center.x, center.y, radius + blur, paint)
    }
}
