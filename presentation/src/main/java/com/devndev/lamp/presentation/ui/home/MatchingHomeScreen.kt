package com.devndev.lamp.presentation.ui.home

import android.media.Image
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.TempDB
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.search.navigation.navigateInvite
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.MoodBlue
import com.devndev.lamp.presentation.ui.theme.MoodRed
import com.devndev.lamp.presentation.ui.theme.MoodYellow
import com.devndev.lamp.presentation.ui.theme.Typography
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun MatchingHomeScreen(modifier: Modifier, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // 아직 방에 유저를 초대할 수 없어 테스트용으로 작업(방의 참여 인원수 2명으로 임시 설정)
    // TODO : 실제 방 참여 인원에 따라 매칭 시작 활성화 되도록 수정
    var currentPersonnel = 2
    val maxPersonnel = when (TempDB.personnel) {
        "2:2" -> 2
        "3:3" -> 3
        "4:4" -> 4
        "5:5" -> 5
        else -> 2
    }

    var fullPersonnel by remember { mutableStateOf(currentPersonnel == maxPersonnel) }
    var isMatching by remember { mutableStateOf(false) }
    val inviteFriend = stringResource(id = R.string.invite_friend)
    val startMatching = stringResource(id = R.string.start_matching)
    val stopMatching = stringResource(id = R.string.stop_matching)

    // fullPersonnel = false : 친구 초대하기 및 스와이프 인식x
    // fullPersonnel = true : 매칭 시작하기 및 스와이프 인식o
    val buttonText = remember(fullPersonnel, isMatching) {
        if (!fullPersonnel) {
            inviteFriend
        } else {
            if (!isMatching) {
                startMatching
            } else {
                stopMatching
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
    ) {
        VerticalSwipeGesture(fullPersonnel, onSwipeUp = {
            isMatching = true
        }, isMatching)
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(LampBlack)
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            MatchingHomeTopBar(
                onExitIconClick = {
                    TempStatus.updateIsMatching(false)
                    TempDB.personnel = ""
                    TempDB.region = ""
                    TempDB.mood = 0
                    TempDB.lampName = ""
                    TempDB.lampSummary = ""
                },
                onShareIconClick = {}
            )
            Text(
                text = stringResource(id = R.string.matching_header_invite),
                color = Color.White,
                style = Typography.semiBold25.copy(lineHeight = 33.sp),
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 원의 최 상단 부분
            Spacer(modifier = Modifier.height(((screenHeight / 7) * 5) - 250.dp))

            Spacer(modifier = Modifier.height(70.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = TempDB.lampName, color = Color.White, style = Typography.semiBold20)
                    Icon(
                        painter = painterResource(
                            id = R.drawable.edit_icon
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigateCreation()
                        }
                    )
                }
                var mood = ""
                when (TempDB.mood) {
                    1 -> mood = stringResource(id = R.string.funny_mood)
                    2 -> mood = stringResource(id = R.string.casual_mood)
                    3 -> mood = stringResource(id = R.string.serious_mood)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LampInfo(
                        painter = painterResource(id = R.drawable.region_icon),
                        text = TempDB.region
                    )
                    LampInfo(
                        painter = painterResource(id = R.drawable.people_icon),
                        text = TempDB.personnel
                    )
                    LampInfo(painter = painterResource(id = R.drawable.heart), text = mood)
                }

                Text(
                    text = TempDB.lampSummary,
                    modifier = Modifier.width(270.dp),
                    maxLines = 3,
                    style = Typography.normal9,
                    color = Gray3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))

                ProfileInfo(null, "북창동루쥬라")

                Spacer(modifier = Modifier.height(30.dp))

                if (!fullPersonnel) {
                    Button(
                        onClick = { navController.navigateInvite() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text(
                            text = buttonText,
                            color = Color.White,
                            style = Typography.medium18,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                } else {
                    LampButton(
                        isGradient = !isMatching,
                        buttonWidth = 300,
                        buttonText = buttonText,
                        onClick = { isMatching = !isMatching },
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun LampInfo(
    painter: Painter,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.White
        )
        Text(text, color = Color.White, style = Typography.normal12)
    }
}

@Composable
fun ProfileInfoList() {
    // todo 추후 친구 초대 가능할 경우 프로필 이미지들 Row로 확장
}

@Composable
fun ProfileInfo(
    image: Image? = null,
    text: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.testimage),
            contentDescription = "testimage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Text(text = text, color = Gray3, style = Typography.normal9)
    }
}

@Composable
fun MatchingHomeTopBar(onExitIconClick: () -> Unit, onShareIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.exit_icon),
            contentDescription = null,
            tint = Gray3,
            modifier = Modifier.clickable {
                onExitIconClick()
            }
        )
        Icon(
            painter = painterResource(id = R.drawable.share_icon),
            contentDescription = null,
            tint = Gray3,
            modifier = Modifier.clickable {
                onShareIconClick()
            }
        )
    }
}

/***
 * 화면 스와이프해서 매칭 시작하기
 */
@Composable
fun VerticalSwipeGesture(fullPersonnel: Boolean, onSwipeUp: () -> Unit, isMatching: Boolean) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val animatableOffset = remember { Animatable(0f) }
    val animatableAlpha = remember { Animatable(0.7f) }
    val animatableShadowRadius = remember { Animatable(150f) }

    // dp -> px 변환을 미리 수행
    val screenHeightPx = with(LocalDensity.current) { screenHeight.toPx() }

    // 애니메이션 처리
    LaunchedEffect(isMatching) {
        if (isMatching) {
            // 원이 위로 이동하며 서서히 사라짐
            coroutineScope {
                launch {
                    animatableOffset.animateTo(
                        targetValue = -screenHeightPx * 0.3f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
                launch {
                    animatableAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
                launch {
                    animatableShadowRadius.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            }

            // 원이 아래에서부터 점점 선명해지며 돌아옴
            animatableOffset.snapTo(screenHeightPx) // 원을 화면 아래로 이동
            animatableAlpha.snapTo(0f) // alpha를 0으로 설정
            animatableShadowRadius.snapTo(250f)

            coroutineScope {
                launch {
                    animatableOffset.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
                launch {
                    animatableAlpha.animateTo(
                        targetValue = 0.7f,
                        animationSpec = tween(durationMillis = 500)
                    )
                }
                launch {
                    animatableShadowRadius.animateTo(
                        targetValue = 250f, // 블러를 서서히 올림
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            }

            // 애니메이션 완료 후 무한 반복 애니메이션 시작
            // shadowRadius 50~250까지 반복
            coroutineScope {
                launch {
                    while (true) {
                        animatableShadowRadius.animateTo(
                            targetValue = 50f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                    }
                }
            }

            onSwipeUp()
        } else {
            animatableOffset.snapTo(0f)
            animatableAlpha.snapTo(0.7f)
            animatableShadowRadius.snapTo(150f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        if (dragAmount < -10 && fullPersonnel) {
                            // 스와이프 업 인식
                            println("Swiped up")
                            // 스와이프 상태를 업데이트
                            onSwipeUp()
                        }
                    }
                )
            }
    ) {
        ShadowCircleBackground(animatableOffset, animatableAlpha, animatableShadowRadius)
    }
}

@Composable
fun ShadowCircleBackground(
    animatableOffset: Animatable<Float, AnimationVector1D>,
    animatableAlpha: Animatable<Float, AnimationVector1D>,
    animatableShadowRadius: Animatable<Float, AnimationVector1D>
) {
    // mood에 따라 색상 변경
    val shadowColor = when (TempDB.mood) {
        1 -> MoodRed.copy(alpha = animatableAlpha.value)
        2 -> MoodYellow.copy(alpha = animatableAlpha.value)
        3 -> MoodBlue.copy(alpha = animatableAlpha.value)
        else -> MoodRed.copy(alpha = animatableAlpha.value)
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Canvas(modifier = Modifier.fillMaxSize()) {
        val size = size
        val shadowRadius = animatableShadowRadius.value
        val center = Offset(size.width / 2, screenHeight.toPx() / 8 * 6 + animatableOffset.value)

        val radius = (screenWidth * (250f / 360f)).toPx()
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                setShadowLayer(
                    shadowRadius,
                    0f,
                    -shadowRadius,
                    shadowColor.toArgb()
                )
                style = android.graphics.Paint.Style.FILL
            }
            canvas.nativeCanvas.drawCircle(center.x, center.y, radius, paint)
        }
        drawCircle(
            color = LampBlack,
            radius = radius,
            center = center
        )
        drawRect(
            color = LampBlack,
            topLeft = Offset(center.x - radius, center.y),
            size = Size(radius * 2, radius)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun B() {
    MatchingHomeScreen(modifier = Modifier, navController = rememberNavController())
}
