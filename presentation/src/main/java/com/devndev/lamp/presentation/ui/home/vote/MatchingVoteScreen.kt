package com.devndev.lamp.presentation.ui.home.vote

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RadialGradient
import android.media.Image
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.devndev.lamp.domain.model.lampmatch.IndividualityDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.MoodBlue
import com.devndev.lamp.presentation.theme.MoodGray
import com.devndev.lamp.presentation.theme.MoodRed
import com.devndev.lamp.presentation.theme.MoodYellow
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup
import com.devndev.lamp.presentation.ui.home.main.HomeViewModel
import com.devndev.lamp.presentation.utils.InstagramUtils
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Locale

@Composable
fun MatchingVoteScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController?,
    onTopScreen: (Boolean) -> Unit,
    onBottomScreen: (Boolean) -> Unit
) {
    val isLoaded by viewModel.isLoaded.collectAsState()
    val matchSuggestion by viewModel.matchSuggestion.collectAsState()
    val approveCount by viewModel.approveCount.collectAsState()
    val rejectCount by viewModel.rejectCount.collectAsState()

    var isAcceptPopupShow by remember { mutableStateOf(false) }
    var isRejectPopupShow by remember { mutableStateOf(false) }
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

    // lamp 데이터
    val lampCnt = if (matchSuggestion != null) {
        matchSuggestion!!.participants.size.plus(1)
    } else {
        null
    }

    val lampProfile = if (matchSuggestion != null) {
        listOf(matchSuggestion!!.name, matchSuggestion!!.location, lampCnt.toString() + "명", matchSuggestion!!.color, matchSuggestion!!.description)
    } else {
        null
    }

    val shouldScrollToTop = rememberSaveable { mutableStateOf(false) }

    // 상대방 lamp 데이터 가져오기
    LaunchedEffect(Unit) {
        onTopScreen(false)
        onBottomScreen(false)
        delay(1000L)

        matchSuggestion?.lampId?.let {
            viewModel.updateSuccessState(it, true)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            onTopScreen(true)
            onBottomScreen(true)
        }
    }

    // Track scroll offset
    LaunchedEffect(listState) {
        // scroll 상태 확인하여 초기화
        if (shouldScrollToTop.value) {
            listState.animateScrollToItem(0)
            shouldScrollToTop.value = false
        }

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

//                isStickyHeaderAtTop.value = index > 1
                // scroll이 조금이라도 된 상태이면 shouldScrollToTop = true
                shouldScrollToTop.value = index != 0 || scrollOffset != 0
            }
    }

    // Spacer 높이를 동적으로 계산
    LaunchedEffect(secondSectionHeight) {
        spacerHeight =
            (screenHeight - (headerSectionHeight + moodInfoSectionHeight + secondSectionHeight + 70.dp + bottomNaviBarHeight.dp))
    }

    if (isAcceptPopupShow) {
        TwoButtonPopup(
            mainText = stringResource(id = R.string.matching_accept_popup_msg),
            startButtonText = stringResource(id = R.string.cancel),
            endButtonText = stringResource(id = R.string.done),
            hintText = stringResource(id = R.string.matching_popup_hint),
            onStartButtonClick = {
                isAcceptPopupShow = false
            },
            onEndButtonClick = {
                if (matchSuggestion != null) {
                    if (!viewModel.isVoted.value) {
                        viewModel.accept(
                            lampSuggestionId = matchSuggestion!!.lampSuggestionId
                        )
                    }
                }
                isAcceptPopupShow = false
            }
        )
    }

    if (isRejectPopupShow) {
        TwoButtonPopup(
            mainText = stringResource(id = R.string.matching_reject_popup_msg),
            startButtonText = stringResource(id = R.string.cancel),
            endButtonText = stringResource(id = R.string.done),
            hintText = stringResource(id = R.string.matching_popup_hint),
            onStartButtonClick = {
                isRejectPopupShow = false
            },
            onEndButtonClick = {
                if (matchSuggestion != null) {
                    if (!viewModel.isVoted.value) {
                        viewModel.reject(
                            lampSuggestionId = matchSuggestion!!.lampSuggestionId
                        )
                    }
                }
                isRejectPopupShow = false
            }
        )
    }
    if (isLoaded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LampBlack)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (lampProfile != null) {
                    ShadowCircleBackground(itemIndex, yOffset, yOffsetHigh, lampProfile)
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .zIndex(1f)
            ) {
                item {
                    Spacer(modifier = Modifier.height(63.dp))
                }

                // Sticky Header with Mood and Info
//            stickyHeader {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .zIndex(10f)
                    ) {
                        if (lampProfile != null) {
                            MoodInfoSection(
                                lampProfile = lampProfile,
                                onHeightChange = { height -> moodInfoSectionHeight = height }
                            )
                        }
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
                            onImageSelected = { selectedImage = it },
                            matchSuggestion = matchSuggestion
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(LampBlack),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        ProfileTop(context = context, matchSuggestion = matchSuggestion, index = selectedImage)
                        Spacer(modifier = Modifier.height(35.dp))
                        ProfileAttractive(matchSuggestion = matchSuggestion, index = selectedImage)
                        Spacer(modifier = Modifier.height(35.dp))
                        ProfileDescription(matchSuggestion = matchSuggestion, index = selectedImage)

                        val density = context.resources.displayMetrics.density
                        val naviBarHeightPx = getNavigationBarHeight(context)
                        val naviBarHeightDp = naviBarHeightPx / density
                        Spacer(modifier = Modifier.height((naviBarHeightDp + 149).dp))
                    }
                }
            }

            BottomSection(
                viewModel = viewModel,
                matchSuggestion = matchSuggestion,
                approveCount = approveCount,
                rejectCount = rejectCount,
                onHeightChange = { height ->
                    bottomSectionHeight = height
                },
                onAcceptClick = {
                    isAcceptPopupShow = true
                },
                onRejectClick = {
                    isRejectPopupShow = true
                }
            )
        }
    }
}

// 프로필 상단부분
@Composable
fun ProfileTop(context: Context, matchSuggestion: MatchSuggestionDomainModel?, index: Int) {
    val listState = remember { LazyListState() }

    // 대학교 list
    val jobNameList: List<String?> = listOf(
        matchSuggestion?.owner?.jobName
    ) + (matchSuggestion?.participants?.map { it.jobName } ?: emptyList())

    // 나이 list
    val ageList = listOf(
        matchSuggestion?.owner?.birth?.let { calculateManAge(it) }
    ) + (matchSuggestion?.participants?.map { it.birth.let { birth -> calculateManAge(birth) } } ?: emptyList())

    // 이름 list
    val nameList = listOfNotNull(
        matchSuggestion?.owner?.name
    ) + matchSuggestion?.participants?.map { it.name }.orEmpty()

    // 프로필 이미지 url list
    val imageUrlList = listOfNotNull(
        matchSuggestion?.owner?.profileImageUrls
    ) + matchSuggestion?.participants?.map { it.profileImageUrls }.orEmpty()

    // 인스타그램 아이디 list
    val instagramList = listOf(
        matchSuggestion?.owner?.instagramId
    ) + matchSuggestion?.participants?.map { it.instagramId }.orEmpty()

    val imageCnt = imageUrlList[index]?.size

    // 프로필 사진 갯수 2개 이하일 경우
    if (imageCnt != null) {
        if (imageCnt <= 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in imageUrlList[index]?.indices!!) {
                    Image(
                        painter = rememberImagePainter(imageUrlList[index]?.get(i)),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(horizontal = 5.dp)
                    )
                }
            }
        } else {
            Box(modifier = Modifier.wrapContentHeight()) {
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .zIndex(0f),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    items(imageUrlList[index]!!.size) { i ->
                        Image(
                            painter = rememberImagePainter(imageUrlList[index]?.get(i)),
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                        )
                    }
                    items(1) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

                val isAtStart by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                    }
                }

                val isAtEnd by remember {
                    derivedStateOf {
                        val layoutInfo = listState.layoutInfo
                        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                        lastVisibleItem != null && lastVisibleItem.index == layoutInfo.totalItemsCount - 1
                    }
                }

                if (isAtStart) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(70.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.8f)
                                    )
                                )
                            )
                            .align(Alignment.CenterEnd)
                    )
                }

                if (isAtEnd) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(70.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.8f),
                                        Color.Transparent
                                    )
                                )
                            )
                            .align(Alignment.CenterStart)
                    )
                }
            }
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
            text = "${nameList[index]} " + stringResource(id = R.string.sir),
            color = if (matchSuggestion?.gender == "MALE") ManColor else WomanColor,
            style = IncTypography.normal42.copy(lineHeight = 56.sp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        if (instagramList[index].toString().isNotEmpty()) {
            Log.d("insta", instagramList.toString())
            Image(
                modifier = Modifier
                    .size(23.dp)
                    .clickable {
                        InstagramUtils.openInstagramProfile(
                            context,
                            instagramList[index].toString() ?: ""
                        )
                    },
                painter = painterResource(id = R.drawable.instagram_icon),
                contentDescription = "Instagram Icon"
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = ageList[index] + stringResource(id = R.string.age) +
                jobNameList[index]?.takeIf { it.isNotBlank() }?.let { ", $it" }.orEmpty(),
            color = Color.White,
            style = Typography.medium18.copy(lineHeight = 20.sp),
            textAlign = TextAlign.Center
        )
    }
}

// 프로필 매력도
@Composable
fun ProfileAttractive(matchSuggestion: MatchSuggestionDomainModel?, index: Int) {
//    var buttonWidth by remember { mutableStateOf(0) }
//    Log.d("matchSuggestion", matchSuggestion.toString())

    // 매력도 리스트
    val attractiveList: List<IndividualityDomainModel?> = listOfNotNull(
        matchSuggestion?.owner?.individuality
    ) + (matchSuggestion?.participants?.map { it.individuality } ?: emptyList())

//    Log.d("attractiveList", attractiveList.toString())
//        matchSuggestion[index][4] as IndividualityDomainModel

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
                text = "${stringResource(id = R.string.attractiveness)} ${attractiveList[index]?.attractiveness}",
                color = Color.White,
                style = Typography.medium18.copy(lineHeight = 20.sp),
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(9.dp))

        ProgressBar(attractiveList, index)
    }
}

@Composable
fun ProgressBar(
    attractiveList: List<IndividualityDomainModel?>,
    index: Int?,
    isMyPage: Boolean = false
) {
    val modifier = if (isMyPage) {
        Modifier
    } else {
        Modifier.fillMaxWidth()
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..3) {
//                val percentage = attractive[i].coerceIn(0, 100) / 100f
                val percentage = when (i) {
                    0 -> attractiveList[index!!]?.personality
                    1 -> attractiveList[index!!]?.voice
                    2 -> attractiveList[index!!]?.fashion
                    3 -> attractiveList[index!!]?.conversation
                    else -> 0
                }?.coerceIn(0, 100)?.div(100f)
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
                            style = Stroke(width = 20f) // 회색 원 스트로크
                        )

                        // 흰색 원 주위의 비율을 채우는 아크 그리기
                        drawArc(
                            color = Color.White,
                            startAngle = -90f, // 12시부터 그리게끔
                            sweepAngle = 360f * percentage!!, // 비율에 따른 각도
                            useCenter = false, // 중심을 사용하지 않음 (경계선만 그리기)
                            style = Stroke(width = 20f) // 스트로크 두께
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
}

// 프로필 설명
@Composable
fun ProfileDescription(matchSuggestion: MatchSuggestionDomainModel?, index: Int) {
    val bioQuestionList: List<List<Pair<String, String>>> = listOf(
        matchSuggestion?.owner?.bioQuestion?.map { it.question to it.answer }.orEmpty(),
        *(
            matchSuggestion?.participants?.map { participant ->
                participant.bioQuestion.map { it.question to it.answer }
            }.orEmpty().toTypedArray()
            )
    )

    val bioList = listOfNotNull(
        matchSuggestion?.owner?.bio
    ) + (matchSuggestion?.participants?.mapNotNull { it.bio } ?: emptyList())
    Log.e("12312312312123", bioList.toString())

    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        text = bioList[index],
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
        bioQuestionList[index].forEachIndexed { i, (question, answer) ->
            Text(
                text = "$question : $answer",
                color = Gray3,
                style = Typography.medium10.copy(lineHeight = 12.sp),
                textAlign = TextAlign.Center
            )
            if (i < bioQuestionList[index].size - 1) {
                Spacer(modifier = Modifier.width(3.dp))
                Icon(
                    painter = painterResource(id = R.drawable.seperate),
                    contentDescription = "Seperate",
                    tint = Gray3,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.width(3.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(25.dp))
}

// 무드 및 정보 섹션
@Composable
fun MoodInfoSection(lampProfile: List<String?>, onHeightChange: (Dp) -> Unit) {
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
            text = lampProfile[0]!!,
            color = Color.White,
            style = Typography.medium25,
            textAlign = TextAlign.Center
        )
        val location = when (lampProfile[1]) {
            "KONDA_SEOUNGSU" -> stringResource(id = R.string.konda_seongsu)
            "SINCHON_HONGDAE" -> stringResource(id = R.string.sinchon_hongdae)
            "GANGNAM_JAMSIL" -> stringResource(id = R.string.gangnam_jamsil)
            "INCHEON" -> stringResource(id = R.string.incheon)
            "GYEONGGI" -> stringResource(id = R.string.gyeonggi)
            else -> ""
        }
        val mood = when (lampProfile[3]) {
            "FUNNY" -> stringResource(id = R.string.funny_mood)
            "CASUAL" -> stringResource(id = R.string.casual_mood)
            "SERIOUS" -> stringResource(id = R.string.serious_mood)
            else -> ""
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OtherLampInfo(
                painter = painterResource(id = R.drawable.region_icon),
                text = location
            )
            OtherLampInfo(
                painter = painterResource(id = R.drawable.people_icon),
                text = lampProfile[2]!!
            )
            OtherLampInfo(painter = painterResource(id = R.drawable.heart), text = mood)
        }
        Text(
            modifier = Modifier.padding(start = 42.5.dp, end = 42.5.dp),
            text = lampProfile[4]!!,
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
    onImageSelected: (Int) -> Unit,
    matchSuggestion: MatchSuggestionDomainModel?
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
        OtherProfileInfo(null, selectedImage, onImageSelected, matchSuggestion)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

// 바닥 섹션
@SuppressLint("DefaultLocale")
@Composable
fun BottomSection(
    viewModel: HomeViewModel,
    matchSuggestion: MatchSuggestionDomainModel?,
    approveCount: Int,
    rejectCount: Int,
    onHeightChange: (Int) -> Unit,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit
) {
    var totalSeconds by remember { mutableStateOf(0L) }
    var hasVote by remember { mutableStateOf(false) }

    var approveButtonColor = if (matchSuggestion?.isApproved == true) {
        if (matchSuggestion.gender == "MALE") {
            ManColor
        } else {
            WomanColor
        }
    } else {
        LightGray
    }

    var rejectButtonColor = if (matchSuggestion?.isApproved == true) {
        if (matchSuggestion.gender == "MALE") {
            ManColor
        } else {
            WomanColor
        }
    } else {
        LightGray
    }

    LaunchedEffect(matchSuggestion) {
        matchSuggestion?.let { suggestion ->
            // matchCompleteTime 문자열을 OffsetDateTime으로 변환
            val targetTime = OffsetDateTime.parse(suggestion.matchCompleteTime)
            val now = OffsetDateTime.now()
            val remaining = Duration.between(now, targetTime).seconds.coerceAtLeast(0L)
            totalSeconds = remaining
        }
    }

    // 타이머 작동
    LaunchedEffect(totalSeconds) {
        if (totalSeconds > 0) {
            delay(1000L)
            totalSeconds -= 1
        } else if (!hasVote) {
            hasVote = true
            matchSuggestion?.lampId?.let { viewModel.reject(it) }
        }
    }

    // 시, 분, 초로 변환
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
//    val hours = TimeUnit.SECONDS.toHours(totalSeconds.toLong()).toInt()
//    val minutes = (TimeUnit.SECONDS.toMinutes(totalSeconds.toLong()) % 60).toInt()
//    val seconds = (totalSeconds % 60)

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
                        colorStops = arrayOf(
                            0.0f to MoodGray.copy(alpha = 0f),
                            0.1f to MoodGray.copy(alpha = 0.5f),
                            1.0f to Gray.copy(alpha = 1f)
                        ),
                        startY = 0f,
                        endY = 100f
//                        endY = Float.POSITIVE_INFINITY
                    )
                )
                .onGloballyPositioned { coordinates ->
                    onHeightChange(coordinates.size.height)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 30.dp, bottom = 15.dp),
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
                        .height(54.dp),
                    onClick = {
                        onAcceptClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = approveButtonColor,
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
                            text = "$approveCount" + "명",
                            style = Typography.medium10,
                            color = Gray3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    onClick = {
                        onRejectClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = rejectButtonColor,
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
                            text = "$rejectCount" + "명",
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
    selectedImage: Int,
    onImageSelected: (Int) -> Unit,
    matchSuggestion: MatchSuggestionDomainModel?
) {
//    // 선택된 이미지 추적
//    var selectedImage by remember { mutableStateOf(0) }

    // 대표 프로필 이미지 url list
    val repImageUrlList = listOf(
        matchSuggestion?.owner?.profileImageUrls?.firstOrNull()
    ) + (matchSuggestion?.participants?.map { it.profileImageUrls.firstOrNull() } ?: emptyList())

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .height(60.dp)
            .clip(CircleShape)
            .background(Gray)
            .padding(start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        if (matchSuggestion != null) {
            for (i in repImageUrlList.indices) {
                repImageUrlList[i]?.let { imageUrl ->
                    SelectableImage(
                        imageUrl = imageUrl,
                        isSelected = selectedImage == i,
                        onClick = { onImageSelected(i) }
                    )
                }
            }
        }
    }
}

// Profile 선택 시 효과
@Composable
fun SelectableImage(
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable { onClick() }
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
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .alpha(if (isSelected) 0.5f else 1f),
            contentScale = ContentScale.Crop // 이미지가 박스 내부에서 꽉 차도록
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
fun ShadowCircleBackground(
    itemIndex: MutableState<Int>,
    yOffset: MutableState<Float>,
    yOffsetHigh: MutableState<Float>,
    lampProfile: List<String?>
) {
    // mood에 따라 색상 변경
    val shadowColor = when (lampProfile[3]) {
        "FUNNY" -> MoodRed.copy(alpha = 0.4f)
        "CASUAL" -> MoodYellow.copy(alpha = 0.4f)
        "SERIOUS" -> MoodBlue.copy(alpha = 0.4f)
        else -> MoodRed.copy(alpha = 0.4f)
    }
    val fillColor = shadowColor.copy(alpha = 0.5f)

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
        val center = Offset(size.width / 2, -screenHeight.toPx() / 5)
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

fun calculateManAge(birthdate: String): String {
    // Check if the birthdate string is valid
    Log.d("MatchingVoteScreen", "birthDate = $birthdate")
    if (birthdate == "00000000") {
        return "0"
    }

    // Parse the birthdate string to a Date object (using the "yyyyMMdd" format)
    val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault()) // Format "19970530"
    val birthDate =
        formatter.parse(birthdate) ?: throw IllegalArgumentException("Invalid birthdate format")

    // Get current date and year
    val birthCalendar = Calendar.getInstance().apply { time = birthDate }

    // Calculate age based on year difference
    var age = Calendar.getInstance().get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    // Check if birthday has occurred this year
    if (
        Calendar.getInstance().get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
        (
            Calendar.getInstance().get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH)
            )
    ) {
        age -= 1
    }
    Log.d("MyPageScreen", "만 나이: $age")
    return age.toString()
}
