package com.devndev.lamp.presentation.ui.review

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.SelectionScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PersonalReviewScreen(step: Int) {
    // 현재 프로그레스 상태를 저장할 상태 변수
    var personalityProgress by remember { mutableStateOf(25f) }
    var voiceProgress by remember { mutableStateOf(25f) }
    var fashionProgress by remember { mutableStateOf(25f) }
    var conversationProgress by remember { mutableStateOf(25f) }
    var previousStep by remember { mutableStateOf(step) }
    val tmpProfile = listOf(
        listOf("닉네임입니다", 28, "한국대학교"),
        listOf("Profile2", 27, "한국대학교"),
        listOf("Profile3", 26, "한국대학교"),
        listOf("Profile4", 25, "한국대학교")
    )

    if (step <= tmpProfile.size) {
        AnimatedContent(
            targetState = step,
            transitionSpec = {
                if (targetState > previousStep) {
                    (slideInHorizontally(initialOffsetX = { it }) + fadeIn()).togetherWith(
                        slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
                    )
                } else {
                    (slideInHorizontally(initialOffsetX = { -it }) + fadeIn()).togetherWith(
                        slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                    )
                }.using(SizeTransform(clip = false))
            }
        ) { currentStep ->
            previousStep = currentStep
            SelectionScreen(text = "${tmpProfile[currentStep - 1][0]}${stringResource(id = R.string.personal_review_title)}") {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 리뷰 slider
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp, end = 30.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = "${tmpProfile[currentStep - 1][0]}${stringResource(id = R.string.personal_review_subtitle)}",
                            style = Typography.medium12,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Image(
                            painter = painterResource(id = R.drawable.testimage),
                            contentDescription = "testimage",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "${tmpProfile[currentStep - 1][1]}${stringResource(id = R.string.age)}, ${tmpProfile[currentStep - 1][2]}",
                            style = Typography.medium15,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(25.dp))

                        PersonalReviewProgressBar("성격은 어땠나요?", true, personalityProgress) { newValue ->
                            personalityProgress = newValue
                        }
                        PersonalReviewProgressBar("목소리는 어땠나요?", true, voiceProgress) { newValue ->
                            voiceProgress = newValue
                        }
                        PersonalReviewProgressBar("패션 센스는 어땠나요?", true, fashionProgress) { newValue ->
                            fashionProgress = newValue
                        }
                        PersonalReviewProgressBar("대화는 어땠나요?", false, conversationProgress) { newValue ->
                            conversationProgress = newValue
                        }
                        Spacer(modifier = Modifier.height(102.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalReviewProgressBar(title: String, isBorder: Boolean, progress: Float, onProgressChange: (Float) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .let {
//                if (isBorder) {
//                    it.then(
//                        Modifier.drawBehind {
//                            val strokeWidth = 1.dp.toPx()
//                            val y = size.height - strokeWidth / 2
//                            drawLine(
//                                color = Gray,
//                                start = Offset(0f, y),
//                                end = Offset(size.width, y),
//                                strokeWidth = strokeWidth
//                            )
//                        }
//                    )
//                } else {
//                    it
//                }
//            }
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            text = title,
            style = Typography.medium18,
            color = Color.White,
            textAlign = TextAlign.Center
        )
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
                .fillMaxWidth(),
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
        Spacer(modifier = Modifier.height(45.dp))
    }
}

@Preview
@Composable
fun C() {
    PersonalReviewScreen(1)
}
