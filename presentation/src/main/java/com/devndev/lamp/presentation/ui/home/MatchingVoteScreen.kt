package com.devndev.lamp.presentation.ui.home

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchingFindScreen(modifier: Modifier, navController: NavController?) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    // Get the current density
    val density = LocalDensity.current
    // Convert screen height to pixels
    val screenHeightPx = with(density) { screenHeight.toPx() }
    val listState = rememberLazyListState()

    var headerSectionHeight by remember { mutableStateOf(0) }
    var spacerHeight by remember { mutableStateOf(0.dp) }
    var moodInfoSectionHeight by remember { mutableStateOf(0) }
    var secondSectionHeight by remember { mutableStateOf(0) }
    var bottomSectionHeight by remember { mutableStateOf(0) }
    var sumHeight by remember { mutableStateOf(0.dp) }

    var sumScrollOffset by remember { mutableStateOf(0) }
    // stickyHeader가 최상단에 위치했는지 여부를 저장하는 상태
    val isStickyHeaderAtTop = remember { mutableStateOf(false) }

    // 스크롤된 오프셋 값을 기억합니다.
    val scrollOffset = listState.firstVisibleItemScrollOffset
    if (listState.firstVisibleItemIndex == 0) {
        sumScrollOffset = scrollOffset
    }

    // Track scroll offset
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
            .collect { (index, scrollOffset) ->
                // 현재 스크롤된 양을 화면 높이로 나누어 index 계산
//                val totalScrolled = index * screenHeightPx + scrollOffset
//                val newIndex = (totalScrolled / screenHeightPx).toInt()
                val newIndex = (((scrollOffset - moodInfoSectionHeight) * 2) / screenHeightPx).toInt()
                Log.d("1111", "index:$index ${((scrollOffset - moodInfoSectionHeight) * 2)}")

                // Update the sticky header visibility based on scroll distance
                isStickyHeaderAtTop.value = index > 0 && newIndex > 0
            }
    }

    // Spacer 높이를 동적으로 계산
    LaunchedEffect(headerSectionHeight, moodInfoSectionHeight) {
        spacerHeight = (screenHeight - (headerSectionHeight + moodInfoSectionHeight + bottomSectionHeight).dp)
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
            ShadowCircleBackground(sumScrollOffset)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
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
                    onHeightChange = { height -> secondSectionHeight = height }
                )
                Spacer(modifier = Modifier.height(70.dp))
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
fun HeaderSection(modifier: Modifier, onHeightChange: (Int) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                onHeightChange(coordinates.size.height)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Text(
            text = stringResource(id = R.string.matching_header_find),
            color = Color.White,
            style = Typography.semiBold25.copy(lineHeight = 33.sp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(id = R.string.matching_sub_header_find),
            color = Color.White,
            style = Typography.semiBold25.copy(lineHeight = 16.sp),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

// 무드 및 정보 섹션
@Composable
fun MoodInfoSection(onHeightChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .zIndex(2f)
            .onGloballyPositioned { coordinates ->
                onHeightChange(coordinates.size.height)
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
    }
}

// 두 번째 섹션
@Composable
fun SecondSection(onHeightChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
            .zIndex(1f)
            .onGloballyPositioned { coordinates ->
                onHeightChange(coordinates.size.height)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = TempDB.lampSummary,
            modifier = Modifier.width(270.dp),
            maxLines = 3,
            style = Typography.normal9,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(6.dp))
        OtherProfileInfo(null, "닉네임입니다")
        // TODO : 스크롤 화살표 이미지
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 58.dp, bottom = 70.dp),
            text = "스크롤",
            style = Typography.normal12,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

// 바닥 섹션
@Composable
fun BottomSection(onHeightChange: (Int) -> Unit) {
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
                text = "매칭 완료까지 02:59:30 남았어요",
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
                        .weight(1f),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp),
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
                        Text(
                            text = "9명 투표",
                            style = Typography.normal12,
                            fontSize = 9.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .weight(1f),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp),
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
fun ShadowCircleBackground(scrollOffset: Int) {
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

    // 원이 상단에서 고정되게 함
    // TODO : scrollOffset 기준 절대값이라 로직 수정 필요
    val yOffset = if (scrollOffset > 1500) -1500 else -scrollOffset

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset(0, yOffset) }
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
