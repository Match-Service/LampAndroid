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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.MoodBlue
import com.devndev.lamp.presentation.theme.MoodGray
import com.devndev.lamp.presentation.theme.MoodRed
import com.devndev.lamp.presentation.theme.MoodYellow
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.main.TempDB
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
//    val isStickyHeaderAtTop = remember { mutableStateOf(false) }
    // 선택된 이미지 추적
    var selectedImage by remember { mutableIntStateOf(0) }

    // lamp 임시 데이터
    val lampProfile = listOf("트와이수더", "신촌,홍대", "4명", "신나는 분위기", "안녕하세요")

    // profile 임시 데이터
    val profiles = listOf(
        listOf("Profile1", 3, 28, "한국대학교", listOf(10, 20, 30, 40), "글자수100글자수100글자수100", listOf(1, 1, 1)),
        listOf("Profile2", 2, 27, "한국대학교", listOf(80, 70, 100, 10), "글자수100글자수100글자수100글자수100글자수100글자수100", listOf(2, 2, 2)),
        listOf("Profile3", 3, 26, "한국대학교", listOf(50, 50, 50, 50), "글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100", listOf(3, 3, 3)),
        listOf("Profile4", 1, 25, "한국대학교", listOf(100, 100, 100, 100), "글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100글자수100", listOf(4, 4, 4))
    )

    val shouldScrollToTop = rememberSaveable { mutableStateOf(false) }

    // Track scroll offset
    LaunchedEffect(listState) {
        // scroll 상태 확인하여 초기화
        if (shouldScrollToTop.value) {
            listState.animateScrollToItem(0)
            shouldScrollToTop.value = false
        }

//        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
//            .collect { (index, scrollOffset) ->
//                if (index > 0) {
//                    curYOffset.value = scrollOffset.toFloat()
//                    yOffset.value += curYOffset.value - prevYOffset.value
//                    prevYOffset.value = curYOffset.value
//                    itemIndex.value = index
//                } else {
//                    yOffset.value = scrollOffset.toFloat()
//                    itemIndex.value = index
//                }
//
//                isStickyHeaderAtTop.value = index > 1
//                // scroll이 조금이라도 된 상태이면 shouldScrollToTop = true
//                shouldScrollToTop.value = index != 0 || scrollOffset != 0
//            }
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
//            ShadowCircleBackground(itemIndex, yOffset, yOffsetHigh)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .zIndex(1f)
        ) {
            item {
//                Spacer(modifier = Modifier.height(spacerHeight))
                Spacer(modifier = Modifier.height(113.dp))
            }

            // Sticky Header with Mood and Info
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
//                        .background(if (isStickyHeaderAtTop.value) Color(0xFF6E2126) else Color.Transparent) // 배경 색상 변경
                        .zIndex(10f)
                ) {
                    MoodInfoSection(lampProfile = lampProfile, onHeightChange = { height -> moodInfoSectionHeight = height })
                }
            }

            // Second Section(조회할 프로필 선택)
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    SecondSection(
                        onHeightChange = { height -> secondSectionHeight = height },
                        selectedImage = selectedImage,
                        onImageSelected = { selectedImage = it }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(LampBlack)
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ProfileTop(profiles = profiles, index = selectedImage)
                    Spacer(modifier = Modifier.height(35.dp))
                    ProfileAttractive(profiles = profiles, index = selectedImage)
                    Spacer(modifier = Modifier.height(35.dp))
                    ProfileDescription(profiles = profiles, index = selectedImage)

                    val density = context.resources.displayMetrics.density
                    val naviBarHeightPx = getNavigationBarHeight(context)
                    val naviBarHeightDp = naviBarHeightPx / density
                    Spacer(modifier = Modifier.height((naviBarHeightDp + 149).dp))
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
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(profiles[index][1] as Int) {
            Image(
                painter = painterResource(id = R.drawable.testimage),
                contentDescription = "testimage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${profiles[index][0]} " + stringResource(id = R.string.sir),
            // TODO: 텍스트 컬러 추가
            color = Color.White,
            style = IncTypography.normal42.copy(lineHeight = 56.sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${profiles[index][2]}" + stringResource(id = R.string.age) + ", " + "${profiles[index][3]}",
            color = Color.White,
            style = Typography.medium18.copy(lineHeight = 20.sp),
            textAlign = TextAlign.Center
        )
    }
}

// 프로필 매력도
@Composable
fun ProfileAttractive(profiles: List<List<Any?>>, index: Int) {
    // MutableState to hold the button's width
    var buttonWidth by remember { mutableStateOf(0) }
    val attractive = profiles[index][4] as? List<Int>
    attractive?.let {
        val attractiveAvg = attractive.sum() / attractive.size
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = "Heart",
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${stringResource(id = R.string.attractiveness)} $attractiveAvg",
                    color = Color.White,
                    style = Typography.medium18.copy(lineHeight = 20.sp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            ProgressBar(attractive)
        }
    }
}

@Composable
fun ProgressBar(attractive: List<Int>, barColor: Color = WomanColor) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(start = 41.5.dp, end = 41.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        for (i in attractive.indices) {
            val percentage = attractive[i].coerceIn(0, 100) / 100f
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    // 원의 반지름
                    val radius = size.minDimension / 2

                    // 회색 원 그리기 (전체 원)
                    drawCircle(
                        color = Gray,
                        radius = radius,
                        style = Stroke(width = 10f) // 회색 원 스트로크
                    )

                    // 흰색 원 주위의 비율을 채우는 아크 그리기
                    drawArc(
                        color = Color.White,
                        startAngle = -90f, // 12시부터 그리게끔
                        sweepAngle = 360f * percentage, // 비율에 따른 각도
                        useCenter = false, // 중심을 사용하지 않음 (경계선만 그리기)
                        style = Stroke(width = 10f) // 스트로크 두께
                    )
                }
                Text(
                    text = when (i) {
                        0 -> stringResource(id = R.string.personality)
                        1 -> stringResource(id = R.string.voice)
                        2 -> stringResource(id = R.string.fashion)
                        3 -> stringResource(id = R.string.conversation)
                        else -> stringResource(id = R.string.personality)
                    },
                    color = Color.White,
                    style = Typography.normal13,
                    textAlign = TextAlign.Center
                )
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
        style = Typography.normal12.copy(lineHeight = 16.sp),
        maxLines = 3
    )
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
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
                style = Typography.medium10.copy(lineHeight = 12.sp),
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

// 무드 및 정보 섹션
@Composable
fun MoodInfoSection(lampProfile: List<String>, onHeightChange: (Dp) -> Unit) {
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
            text = lampProfile[0],
            color = Color.White,
            style = Typography.medium25,
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
            OtherLampInfo(painter = painterResource(id = R.drawable.region_icon), text = lampProfile[1])
            OtherLampInfo(painter = painterResource(id = R.drawable.people_icon), text = lampProfile[2])
            OtherLampInfo(painter = painterResource(id = R.drawable.heart), text = lampProfile[3])
        }
        Text(
            text = lampProfile[4],
            color = Gray3,
            style = Typography.medium10,
            textAlign = TextAlign.Center
        )
    }
}

// 두 번째 섹션
@Composable
fun SecondSection(
    onHeightChange: (Dp) -> Unit,
    selectedImage: Int,
    onImageSelected: (Int) -> Unit
) {
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
        Spacer(modifier = Modifier.height(41.dp))
        OtherProfileInfo(null, "닉네임입니다", selectedImage, onImageSelected)
        Spacer(modifier = Modifier.height(50.dp))
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
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MoodGray.copy(alpha = 1f),
                            Gray.copy(alpha = 1f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
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
                    Row(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "수락하기",
                            style = Typography.medium18,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "9명",
                            style = Typography.medium10,
                            color = Gray3,
                            textAlign = TextAlign.Center
                        )
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
                    Row(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "거절하기",
                            style = Typography.medium18,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "9명",
                            style = Typography.medium10,
                            color = Gray3,
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
    text: String,
    selectedImage: Int,
    onImageSelected: (Int) -> Unit
) {
//    // 선택된 이미지 추적
//    var selectedImage by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .width(225.dp)
            .height(60.dp)
            .clip(CircleShape)
            .background(Gray)
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        SelectableImage(
            imageRes = R.drawable.testimage,
            isSelected = selectedImage == 0,
            onClick = { onImageSelected(0) }
        )
        SelectableImage(
            imageRes = R.drawable.testimage,
            isSelected = selectedImage == 1,
            onClick = { onImageSelected(1) }
        )
        SelectableImage(
            imageRes = R.drawable.testimage,
            isSelected = selectedImage == 2,
            onClick = { onImageSelected(2) }
        )
        SelectableImage(
            imageRes = R.drawable.testimage,
            isSelected = selectedImage == 3,
            onClick = { onImageSelected(3) }
        )
    }
}

// Profile 선택 시 효과
@Composable
fun SelectableImage(
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .alpha(if (isSelected) 0.5f else 1f)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                } else {
                    Modifier // 선택되지 않은 경우 border 없음
                }
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.testimage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // 가운데 하트 추가
        if (isSelected) {
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp) // 작은 크기의 선택된 이미지
                    .align(Alignment.Center) // 가운데 정렬
            )
        }
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

@Preview
@Composable
fun A() {
    MatchingVoteScreen(modifier = Modifier, navController = rememberNavController())
}
