package com.devndev.lamp.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.MoodBlue
import com.devndev.lamp.presentation.theme.MoodRed
import com.devndev.lamp.presentation.theme.MoodYellow
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.search.navigation.navigateInvite
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun MatchingHomeScreen(
    modifier: Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val myLamp by homeViewModel.myLamp.collectAsState()
    val myInfo by homeViewModel.myInfo.collectAsState()
    val isOwner = myLamp?.lamp?.owner?.userId == myInfo?.userId
    var isDeletePopupShow by remember { mutableStateOf(false) }
    var isExitPopupShow by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val navOption = navOptions {
        launchSingleTop = true
    }

    if (isDeletePopupShow) {
        TwoButtonPopup(
            mainText = stringResource(id = R.string.lamp_out_popup_main, myLamp!!.lamp!!.name),
            startButtonText = stringResource(id = R.string.cancel),
            endButtonText = stringResource(id = R.string.out),
            hintText = stringResource(id = R.string.lamp_out_hint),
            onStartButtonClick = { isDeletePopupShow = false },
            onEndButtonClick = {
                homeViewModel.deleteLamp()
                isDeletePopupShow = false
            }
        )
    }

    if (isExitPopupShow) {
        TwoButtonPopup(
            mainText = stringResource(id = R.string.lamp_out_popup_main, myLamp!!.lamp!!.name),
            startButtonText = stringResource(id = R.string.cancel),
            endButtonText = stringResource(id = R.string.out),
            onStartButtonClick = { isExitPopupShow = false },
            onEndButtonClick = {
                homeViewModel.exitLamp()
                isExitPopupShow = false
            }
        )
    }

    // 아직 방에 유저를 초대할 수 없어 테스트용으로 작업(방의 참여 인원수 2명으로 임시 설정)
    // TODO : 실제 방 참여 인원에 따라 매칭 시작 활성화 되도록 수정
    var currentPersonnel = 2
    val maxPersonnel = myLamp?.lamp?.hopeMatchNumber

    var fullPersonnel by remember { mutableStateOf(currentPersonnel == maxPersonnel) }
    var isMatching by remember { mutableStateOf(false) }
    var lampTitle by remember { mutableStateOf("") }
    val inviteFriend = stringResource(id = R.string.invite_friend)
    val startMatching = stringResource(id = R.string.start_matching)
    val stopMatching = stringResource(id = R.string.stop_matching)
    val pleaseInviteFriend = stringResource(id = R.string.matching_header_invite)
    val pleaseStartMatching = stringResource(id = R.string.matching_header_start_matching)
    val whileMatching = stringResource(id = R.string.matching_header_while_invite)

    // fullPersonnel = false : 친구 초대하기 및 스와이프 인식x
    // fullPersonnel = true : 매칭 시작하기 및 스와이프 인식o
    val buttonText = remember(fullPersonnel, isMatching) {
        if (!fullPersonnel) {
            lampTitle = pleaseInviteFriend
            inviteFriend
        } else {
            if (!isMatching) {
                lampTitle = pleaseStartMatching
                startMatching
            } else {
                lampTitle = whileMatching
                stopMatching
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
            .clipToBounds()
    ) {
        VerticalSwipeGesture(
            fullPersonnel,
            onSwipeUp = {
                isMatching = true
            },
            isMatching,
            mood = myLamp?.lamp?.color
        )
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
            verticalArrangement = Arrangement.spacedBy(47.dp)
        ) {
            MatchingHomeTopBar(
                onExitIconClick = {
                    if (isOwner) {
                        isDeletePopupShow = true
                    } else {
                        isExitPopupShow = true
                    }
                },
                onShareIconClick = {}
            )
            Text(
                text = lampTitle,
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
                    myLamp?.lamp?.name?.let {
                        Text(
                            text = it,
                            color = Color.White,
                            style = Typography.semiBold20
                        )
                    }
                    Icon(
                        painter = painterResource(
                            id = R.drawable.edit_icon
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigateCreation(navOption)
                        }
                    )
                }
                var mood = ""
                when (myLamp?.lamp?.color) {
                    "FUNNY" -> mood = stringResource(id = R.string.funny_mood)
                    "CASUAL" -> mood = stringResource(id = R.string.casual_mood)
                    "SERIOUS" -> mood = stringResource(id = R.string.serious_mood)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LampInfo(
                        painter = painterResource(id = R.drawable.region_icon),
                        text = convertLocation(myLamp?.lamp?.location)
                    )
                    LampInfo(
                        painter = painterResource(id = R.drawable.people_icon),
                        text = "${myLamp?.lamp?.hopeMatchNumber}:${myLamp?.lamp?.hopeMatchNumber}"
                    )
                    LampInfo(painter = painterResource(id = R.drawable.heart), text = mood)
                }

                myLamp?.lamp?.description?.let {
                    Text(
                        text = it,
                        modifier = Modifier.width(270.dp),
                        maxLines = 3,
                        style = Typography.normal9,
                        color = Gray3,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                myLamp?.let { ProfileInfoList(it, isOwner) }

                Spacer(modifier = Modifier.height(30.dp))

                if (!fullPersonnel) {
                    Button(
                        onClick = { navController.navigateInvite(navOption) },
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

fun convertLocation(selectedRegion: String?): String {
    return when (selectedRegion) {
        "KONDA_SEOUNGSU" -> "건대·성수"
        "SINCHON_HONGDAE" -> "신촌·홍대"
        "GANGNAM_JAMSIL" -> "강남·잠실"
        "INCHEON" -> "인천"
        "GYEONGGI" -> "경기"
        else -> throw IllegalArgumentException("Invalid region selected: $selectedRegion")
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
fun ProfileInfoList(myLamp: LampDomainModel, isOwner: Boolean) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.animateContentSize()
    ) {
        ProfileInfo(
            myLamp.lamp?.owner?.profileImageUrl,
            myLamp.lamp?.owner?.name ?: "",
            true,
            isOwner
        )

        myLamp.lamp?.participants?.forEach { participant ->
            ProfileInfo(participant.profileImageUrl, participant.name, false, isOwner)
        }
    }
}

@Composable
fun ProfileInfo(
    url: String?,
    text: String,
    isOwnerProfile: Boolean,
    isOwner: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 3.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.size(40.dp)
        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .then(
                        if (isOwnerProfile) {
                            Modifier.border(0.5.dp, Color.White, CircleShape)
                        } else {
                            Modifier
                        }
                    )
            )
            if (isOwnerProfile) {
                Box() {
                    Icon(
                        painter = painterResource(id = R.drawable.owner_icon_out),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.owner_icon_in),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else if (isOwner) {
                Icon(
                    painter = painterResource(id = R.drawable.x_circle_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.clickable {
                        // 강퇴
                    }
                )
            }
        }
        Text(text = text, color = Color.White, style = Typography.normal9)
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
fun VerticalSwipeGesture(
    fullPersonnel: Boolean,
    onSwipeUp: () -> Unit,
    isMatching: Boolean,
    mood: String?
) {
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
                        animationSpec = tween(durationMillis = 750)
                    )
                }
                launch {
                    animatableAlpha.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 750)
                    )
                }
                launch {
                    animatableShadowRadius.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 750)
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
                        animationSpec = tween(durationMillis = 750)
                    )
                }
                launch {
                    animatableAlpha.animateTo(
                        targetValue = 0.7f,
                        animationSpec = tween(durationMillis = 750)
                    )
                }
                launch {
                    animatableShadowRadius.animateTo(
                        targetValue = 250f, // 블러를 서서히 올림
                        animationSpec = tween(durationMillis = 750)
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
        ShadowCircleBackground(animatableOffset, animatableAlpha, animatableShadowRadius, mood)
    }
}

@Composable
fun ShadowCircleBackground(
    animatableOffset: Animatable<Float, AnimationVector1D>,
    animatableAlpha: Animatable<Float, AnimationVector1D>,
    animatableShadowRadius: Animatable<Float, AnimationVector1D>,
    mood: String?
) {
    // mood에 따라 색상 변경
    val shadowColor = when (mood) {
        "FUNNY" -> MoodRed.copy(alpha = animatableAlpha.value)
        "CASUAL" -> MoodYellow.copy(alpha = animatableAlpha.value)
        "SERIOUS" -> MoodBlue.copy(alpha = animatableAlpha.value)
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
