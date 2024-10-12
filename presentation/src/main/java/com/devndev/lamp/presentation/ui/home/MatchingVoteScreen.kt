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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
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
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.IncTypography
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.MoodBlue
import com.devndev.lamp.presentation.ui.theme.MoodRed
import com.devndev.lamp.presentation.ui.theme.MoodYellow
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor
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

    // profile 임시 데이터
    val profiles = listOf(
        listOf("Profile1", 3, 28, "한국대학교", null, "글자수100글자수100글자수100", listOf(1, 1, 1)),
        listOf("Profile2", 2, 27, "한국대학교", listOf(80, 70, 100, 10), "글자수100글자수100글자수100글자수100글자수100글자수100", listOf(2, 2, 2)),
        listOf("Profile3", 3, 26, "한국대학교", listOf(50, 50, 50, 50), "글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100", listOf(3, 3, 3)),
        listOf("Profile4", 1, 25, "한국대학교", listOf(100, 100, 100, 100), "글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100", listOf(4, 4, 4))
    )

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
        spacerHeight = (screenHeight - (headerSectionHeight + moodInfoSectionHeight + secondSectionHeight + 70.dp + bottomNaviBarHeight.dp))
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
                    onHeightChange = { height -> secondSectionHeight = height }
                )
            }

            // Additional items to create scrollable area
            items(profiles.size) { index -> // Adjust the item count to ensure scrolling
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Black)
                        .padding(start = 30.dp, end = 30.dp)
                        .then(
                            if (index < profiles.size - 1) {
                                Modifier.drawBehind {
                                    val strokeWidth = 1.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color.Gray,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                            } else {
                                Modifier // 마지막 항목에는 border 추가하지 않음
                            }
                        )
                ) {
                    ProfileTop(profiles, index)
                    Spacer(modifier = Modifier.height(20.dp))
                    ProfileAttractive(profiles, index)
                    ProfileDescription(profiles, index)
                    if (index == profiles.size - 1) {
                        val density = context.resources.displayMetrics.density
                        val naviBarHeightPx = getNavigationBarHeight(context)
                        val naviBarHeightDp = naviBarHeightPx / density
                        Spacer(modifier = Modifier.height((naviBarHeightDp + 149).dp))
                    }
                }
            }
        }

        BottomSection(
            onHeightChange = { height ->
                bottomSectionHeight = height
            }
        )
    }
}

// 프로필 상단부분
@Composable
fun ProfileTop(profiles: List<List<Any?>>, index: Int) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(profiles[index][1] as Int) {
            Image(
                painter = painterResource(id = R.drawable.testimage),
                contentDescription = "testimage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RectangleShape)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${profiles[index][0]} " + stringResource(id = R.string.sir),
            color = Color.White,
            style = IncTypography.normal42.copy(lineHeight = 40.sp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier
                .size(23.dp)
                .clickable { /*TODO : 인스타 계정으로 이동하도록 구현 필요*/ },
            painter = painterResource(id = R.drawable.instagram_icon),
            contentDescription = "Instagram Icon"
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${profiles[index][2]}" + stringResource(id = R.string.age) + ", " + "${profiles[index][3]}",
            color = Color.White,
            style = Typography.semiBold25.copy(lineHeight = 20.sp),
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

// 프로필 매력도
@Composable
fun ProfileAttractive(profiles: List<List<Any?>>, index: Int) {
    // MutableState to hold the button's width
    var buttonWidth by remember { mutableStateOf(0) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val attractive = profiles[index][4] as? List<Int>
    attractive?.let {
        val attractiveAvg = attractive.sum() / attractive.size
        // Create a gradient brush
        val gradientBrush = when {
            attractiveAvg == 0 -> {
                // If attractiveness is 0, the color is fully gray
                Brush.horizontalGradient(
                    colors = listOf(Gray, Gray)
                )
            }
            attractiveAvg < 100 && attractiveAvg > 0 -> {
                // If attractiveness is between 1 and 99, create a red-to-gray gradient
                val redWidth = buttonWidth * (attractiveAvg / 100f) // Calculate the ratio for red (0.0 to 1.0)
                Brush.horizontalGradient(
                    colors = listOf(WomanColor, Gray),
                    startX = redWidth - 50f,
                    endX = redWidth + 50f // Use the calculated redWidth for the gradient
                )
            }
            else -> {
                // If attractiveness is 100, the color is fully red
                Brush.horizontalGradient(
                    colors = listOf(WomanColor, WomanColor)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { isDropdownExpanded = !isDropdownExpanded },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .onSizeChanged { size ->
                        buttonWidth = size.width // Capture the button width
                    }
                    .clip(RoundedCornerShape(27.dp))
                    .background(gradientBrush)
                    .padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        contentDescription = "Heart",
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${stringResource(id = R.string.attractiveness)} $attractiveAvg",
                        color = Color.White,
                        style = Typography.semiBold25.copy(lineHeight = 20.sp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = if (isDropdownExpanded) painterResource(id = R.drawable.reduce_icon) else painterResource(id = R.drawable.expand_icon),
                        contentDescription = "Expand",
                        tint = Color.White,
                        modifier = Modifier
                            .size(10.dp)
                    )
                }
            }

            // Conditionally show the Row based on the state
            if (isDropdownExpanded) {
                Spacer(modifier = Modifier.height(10.dp)) // Space between button and dropdown
                // The Row that gets shown/hidden
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProgressBar(attractive)
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp)) // Space between button and dropdown
            }
        }
    }
}

@Composable
fun ProgressBar(attractive: List<Int>) {
    for (i in attractive.indices) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .width(36.dp),
                text = when (i) {
                    0 -> stringResource(id = R.string.personality)
                    1 -> stringResource(id = R.string.voice)
                    2 -> stringResource(id = R.string.fashion)
                    3 -> stringResource(id = R.string.conversation)
                    else -> stringResource(id = R.string.personality)
                },
                color = Color.White,
                style = Typography.semiBold25.copy(lineHeight = 16.sp),
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )

            // Ensure the value is between 0 and 100
            val percentage = attractive[i].coerceIn(0, 100) / 100f

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp) // Set the desired height for the progress bar
                    .background(Gray, RoundedCornerShape(40.dp)) // Gray background bar
                    .clip(RoundedCornerShape(40.dp)) // Rounded corners for the gray bar
            ) {
                // Red bar representing the value
                Box(
                    modifier = Modifier
                        .fillMaxHeight() // Match height of the gray bar
                        .fillMaxWidth(percentage) // Width based on percentage
                        .background(WomanColor, RoundedCornerShape(40.dp)) // Red bar for the filled portion
                )
            }
            // 매력도 사이 간격
            if (i < attractive.size - 1) {
                Spacer(modifier = Modifier.height(5.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// 프로필 설명
@Composable
fun ProfileDescription(profiles: List<List<Any?>>, index: Int) {
    Text(
        text = "${profiles[index][5]}",
        color = Color.White,
        style = Typography.semiBold25.copy(lineHeight = 16.sp),
        fontSize = 12.sp,
        textAlign = TextAlign.Start,
        maxLines = 3
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        val profileInfo = profiles[index][6] as List<Int>
        for (i in profileInfo.indices) {
            Text(
                text = when (i) {
                    0 -> {
                        "${stringResource(id = R.string.drink)} : " + when (profileInfo[i]) {
                            1 -> stringResource(id = R.string.drink_often)
                            2 -> stringResource(id = R.string.drink_sometimes)
                            3 -> stringResource(id = R.string.drink_never)
                            4 -> stringResource(id = R.string.drink_stop)
                            else -> {}
                        }
                    }
                    1 -> {
                        "${stringResource(id = R.string.smoke)} : " + when (profileInfo[i]) {
                            1 -> stringResource(id = R.string.smoke_often)
                            2 -> stringResource(id = R.string.smoke_sometimes)
                            3 -> stringResource(id = R.string.smoke_never)
                            4 -> stringResource(id = R.string.smoke_stop)
                            else -> {}
                        }
                    }
                    2 -> {
                        "${stringResource(id = R.string.exercise)} : " + when (profileInfo[i]) {
                            1 -> stringResource(id = R.string.exercise_often)
                            2 -> stringResource(id = R.string.exercise_sometimes)
                            3 -> stringResource(id = R.string.exercise_never)
                            4 -> stringResource(id = R.string.exercise_stop)
                            else -> {}
                        }
                    }
                    else -> "" // nothing
                },
                color = Gray3,
                style = Typography.semiBold25.copy(lineHeight = 12.sp),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
            if (i < profileInfo.size - 1) {
                Icon(
                    painter = painterResource(id = R.drawable.seperate),
                    contentDescription = "Seperate",
                    tint = Gray3,
                    modifier = Modifier
                        .wrapContentSize()
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(25.dp))
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
fun SecondSection(onHeightChange: (Dp) -> Unit) {
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
        Spacer(modifier = Modifier.height(145.dp))
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
