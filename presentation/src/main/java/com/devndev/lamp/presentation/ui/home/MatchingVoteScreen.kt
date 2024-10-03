package com.devndev.lamp.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RadialGradient
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.TempDB
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.MoodBlue
import com.devndev.lamp.presentation.ui.theme.MoodRed
import com.devndev.lamp.presentation.ui.theme.MoodYellow
import com.devndev.lamp.presentation.ui.theme.Typography
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchingVoteScreen(modifier: Modifier, navController: NavController?) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val bottomNaviBarHeight = getNavigationBarHeight(context)
    val screenHeight = configuration.screenHeightDp.dp
    // Get the current density
    val density = LocalDensity.current
    // Convert screen height to pixels
    val screenHeightPx = with(density) { screenHeight.toPx() }
    val listState = rememberLazyListState()

    var headerSectionHeight by remember { mutableStateOf(0.dp) }
    var spacerHeight by remember { mutableStateOf(0.dp) }
    var moodInfoSectionHeight by remember { mutableStateOf(0.dp) }
    var secondSectionHeight by remember { mutableStateOf(0.dp) }
    var bottomSectionHeight by remember { mutableStateOf(0) }

    var itemIndex = remember { mutableStateOf(0) }
    var yOffset = remember { mutableStateOf(0f) }
    var yOffsetHigh = remember { mutableStateOf(0f) }
    var prevYOffset = remember { mutableStateOf(0f) }
    var curYOffset = remember { mutableStateOf(0f) }
    // stickyHeader가 최상단에 위치했는지 여부를 저장하는 상태
    val isStickyHeaderAtTop = remember { mutableStateOf(false) }

    // Track scroll offset
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, scrollOffset) ->
                if (index > 0) {
                    curYOffset.value = scrollOffset.toFloat()
                    yOffset.value += curYOffset.value - prevYOffset.value
                    prevYOffset.value = curYOffset.value
                    itemIndex.value = index
                } else {
                    yOffset.value = scrollOffset.toFloat()
                    itemIndex.value = index
                }
                // Update the sticky header visibility based on scroll distance
                isStickyHeaderAtTop.value = index > 1
            }
    }

    // Spacer 높이를 동적으로 계산
    LaunchedEffect(secondSectionHeight) {
        spacerHeight = (screenHeight - (headerSectionHeight + moodInfoSectionHeight + secondSectionHeight + 70.dp))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ShadowCircleBackground(itemIndex, yOffset, yOffsetHigh)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .zIndex(1f)
        ) {
            // Header Section
            item {
                HeaderSection(
                    modifier = modifier,
                    onHeightChange = { height -> headerSectionHeight = height }
                )
            }

            item {
                Spacer(modifier = Modifier.height(spacerHeight))
                Log.d("spacerHeight", "$spacerHeight")
            }

            // Sticky Header with Mood and Info
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isStickyHeaderAtTop.value) Color(0xFF6E2126) else Color.Transparent) // 배경 색상 변경
                        .zIndex(10f)
                ) {
                    MoodInfoSection(onHeightChange = { height -> moodInfoSectionHeight = height })
                }
            }

            // Second Section
            item {
                SecondSection(
                    bottomNaviBarHeight,
                    onHeightChange = { height -> secondSectionHeight = height }
                )
            }

            // Additional items to create scrollable area
            items(5) { index -> // Adjust the item count to ensure scrolling
                Text(
                    text = "상대방${index + 1}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black)
                        .padding(16.dp)
                        .zIndex(1f),
                    color = Color.White
                )
            }
        }

        BottomSection(
            onHeightChange = { height ->
                bottomSectionHeight = height
            }
        )
    }
}

// 헤더 섹션
@Composable
fun HeaderSection(modifier: Modifier, onHeightChange: (Dp) -> Unit) {
    val density = LocalDensity.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val headerSectionHeightDp = with(density) {
                    coordinates.size.height.toDp()
                }
                onHeightChange(headerSectionHeightDp)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Text(
            text = "인연의 불빛을 발견했어요",
            color = Color.White,
            style = Typography.semiBold25.copy(lineHeight = 33.sp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "상대의 램프를 확인 해보세요",
            color = Color.White,
            style = Typography.semiBold25.copy(lineHeight = 16.sp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

// 무드 및 정보 섹션
@Composable
fun MoodInfoSection(onHeightChange: (Dp) -> Unit) {
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(2f)
            .onGloballyPositioned { coordinates ->
                val moodInfoHeightDp = with(density) {
                    coordinates.size.height.toDp()
                }
                onHeightChange(moodInfoHeightDp)
            },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "1",
            color = Color.White,
            style = Typography.semiBold20,
            textAlign = TextAlign.Center
        )
        val mood = when (TempDB.mood) {
            1 -> stringResource(id = R.string.funny_mood)
            2 -> stringResource(id = R.string.casual_mood)
            3 -> stringResource(id = R.string.serious_mood)
            else -> ""
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OtherLampInfo(painter = painterResource(id = R.drawable.region_icon), text = TempDB.region)
            OtherLampInfo(painter = painterResource(id = R.drawable.people_icon), text = TempDB.personnel)
            OtherLampInfo(painter = painterResource(id = R.drawable.heart), text = mood)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

// 두 번째 섹션
@Composable
fun SecondSection(bottomNaviBarHeight: Int, onHeightChange: (Dp) -> Unit) {
    val density = LocalDensity.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .zIndex(1f)
            .onGloballyPositioned { coordinates ->
                val secondSectionHeightDp = with(density) {
                    coordinates.size.height.toDp()
                }
                onHeightChange(secondSectionHeightDp)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = TempDB.lampSummary,
            modifier = Modifier
                .width(270.dp)
                .wrapContentHeight(),
            maxLines = 3,
            style = Typography.normal9,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        OtherProfileInfo(null, "닉네임입니다")
        Spacer(modifier = Modifier.height(58.dp))
        Icon(
            painter = painterResource(id = R.drawable.scroll_arrow),
            contentDescription = "Check",
            tint = Color.White,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            modifier = Modifier
                .wrapContentWidth(),
            text = "스크롤하여 상대의 프로필을 확인하세요",
            style = Typography.normal12,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height((145 + bottomNaviBarHeight).dp))
    }
}

// 바닥 섹션
@SuppressLint("DefaultLocale")
@Composable
fun BottomSection(onHeightChange: (Int) -> Unit) {
    // 초기 시간을 3시간(03:00:00)으로 설정
    var totalSeconds by remember { mutableStateOf(3 * 60 * 60) }

    // 1초마다 시간을 줄이는 타이머
    LaunchedEffect(key1 = totalSeconds) {
        if (totalSeconds > 0) {
            delay(1000L)
            totalSeconds -= 1
        }
    }

    // 시, 분, 초로 변환
    val hours = TimeUnit.SECONDS.toHours(totalSeconds.toLong()).toInt()
    val minutes = (TimeUnit.SECONDS.toMinutes(totalSeconds.toLong()) % 60).toInt()
    val seconds = (totalSeconds % 60)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
            .zIndex(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(Gray)
                .onGloballyPositioned { coordinates ->
                    onHeightChange(coordinates.size.height)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = "매칭 완료까지 ${String.format("%02d:%02d:%02d", hours, minutes, seconds)} 남았어요",
                style = Typography.normal12,
                fontSize = 12.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "수락하기",
                            style = Typography.normal12,
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.vote),
                                contentDescription = "Vote",
                                tint = Color.White
                            )
                            Text(
                                text = "9명 투표",
                                style = Typography.normal12,
                                fontSize = 9.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "거절하기",
                            style = Typography.normal12,
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentSize(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.vote),
                                contentDescription = "Vote",
                                tint = Color.White
                            )
                            Text(
                                text = "9명 투표",
                                style = Typography.normal12,
                                fontSize = 9.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OtherLampInfo(
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
fun OtherProfileInfoList() {
    // todo 추후 친구 초대 가능할 경우 프로필 이미지들 Row로 확장
}

@Composable
fun OtherProfileInfo(
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
        Text(text = text, color = Color.White, style = Typography.normal9)
    }
}

@Composable
fun ShadowCircleBackground(itemIndex: MutableState<Int>, yOffset: MutableState<Float>, yOffsetHigh: MutableState<Float>) {
    // mood에 따라 색상 변경
    val shadowColor = when (TempDB.mood) {
        1 -> MoodRed.copy(alpha = 0.4f)
        2 -> MoodYellow.copy(alpha = 0.4f)
        3 -> MoodBlue.copy(alpha = 0.4f)
        else -> MoodRed.copy(alpha = 0.4f)
    }
    val fillColor = shadowColor.copy(alpha = 0.3f)

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // 스크롤 오프셋이 stickyHeader 가 넘기 전까지는 스크롤 업데이트
    val adjustedOffset = if (itemIndex.value > 1) {
        yOffsetHigh.value
    } else {
        yOffsetHigh.value = yOffset.value
        yOffset.value
    }
    Log.d("adjustOffset", "$adjustedOffset")

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(0, -adjustedOffset.toInt()) }
    ) {
        val size = size
        val shadowRadius = 50.dp.toPx()
        val center = Offset(size.width / 2, screenHeight.toPx() / 7 * 5)
        val radius = 400.dp.toPx()
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                shader = RadialGradient(
                    center.x,
                    center.y,
                    radius,
                    intArrayOf(
                        shadowColor.toArgb(),
                        fillColor.toArgb(),
                        android.graphics.Color.TRANSPARENT
                    ),
                    floatArrayOf(0.3f, 0.6f, 1f), // 색상 위치 (그라데이션 진행도)
                    android.graphics.Shader.TileMode.CLAMP // 그라데이션 방식
                )
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
    }
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun getNavigationBarHeight(context: Context): Int {
    val resources = context.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}
