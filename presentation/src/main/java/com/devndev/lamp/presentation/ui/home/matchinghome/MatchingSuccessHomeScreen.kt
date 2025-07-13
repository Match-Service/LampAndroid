package com.devndev.lamp.presentation.ui.home.matchinghome

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.MoodBlue
import com.devndev.lamp.presentation.theme.MoodRed
import com.devndev.lamp.presentation.theme.MoodYellow
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.chatting.ChatActivity
import com.devndev.lamp.presentation.ui.chatting.ChatViewModel
import com.devndev.lamp.presentation.ui.chatting.navigation.navigateChatList
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.Route
import com.devndev.lamp.presentation.ui.home.main.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun MatchingSuccessHomeScreen(
    modifier: Modifier,
    navController: NavController,
    updateEvent: SharedFlow<Unit>,
    updateStatus: SharedFlow<String>,
    onBottomScreen: (Boolean) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val matchSuggestion by viewModel.matchSuggestion.collectAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val context = LocalContext.current
    val activity = context as? Activity
    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatList by chatViewModel.uiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    val chattingNavOptions = navOptions {
        launchSingleTop = true
        popUpTo(Route.CHAT_LIST) { inclusive = true }
    }

    LaunchedEffect(Unit) {
        onBottomScreen(true)

        matchSuggestion?.lampId?.let { viewModel.updateFindState(it, false) }
    }

    DisposableEffect(Unit) {
        onDispose {
            onBottomScreen(true)
        }
    }

    val headerSuccessMatching = stringResource(id = R.string.matching_header_success)
    val subHeaderSuccessMatching =
        matchSuggestion?.name?.let { stringResource(id = R.string.matching_sub_header_success, it) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
            .clipToBounds()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MatchSuccessShadowCircleBackground(matchSuggestion)
        }

        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = headerSuccessMatching,
                color = Color.White,
                style = Typography.semiBold25.copy(lineHeight = 33.sp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(7.dp))
            if (subHeaderSuccessMatching != null) {
                Text(
                    text = subHeaderSuccessMatching,
                    color = Color.White,
                    style = Typography.normal12.copy(lineHeight = 16.sp),
                    textAlign = TextAlign.Center
                )
            }
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
                    matchSuggestion?.name.let {
                        if (it != null) {
                            Text(
                                text = it,
                                color = Color.White,
                                style = Typography.semiBold20
                            )
                        }
                    }
                }
                var mood = ""
                when (matchSuggestion?.color) {
                    "FUNNY" -> mood = stringResource(id = R.string.funny_mood)
                    "CASUAL" -> mood = stringResource(id = R.string.casual_mood)
                    "SERIOUS" -> mood = stringResource(id = R.string.serious_mood)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LampSuccessInfo(
                        painter = painterResource(id = R.drawable.region_icon),
                        text = convertLocation(matchSuggestion?.location)
                    )
                    LampSuccessInfo(
                        painter = painterResource(id = R.drawable.people_icon),
                        text = "${matchSuggestion?.hopeMatchNumber ?: "0"}:${matchSuggestion?.hopeMatchNumber ?: "0"}"
                    )
                    LampSuccessInfo(painter = painterResource(id = R.drawable.heart), text = mood)
                }

                matchSuggestion?.description.let {
                    if (it != null) {
                        Text(
                            text = it,
                            modifier = Modifier.width(270.dp),
                            maxLines = 3,
                            style = Typography.normal9,
                            color = Gray3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                ProfileSuccessInfoList(
                    matchSuggestion = matchSuggestion
                )

                Spacer(modifier = Modifier.height(30.dp))

                LampButton(
                    isGradient = true,
                    buttonWidth = 171,
                    buttonText = stringResource(id = R.string.join_chat),
                    onClick = {
                        val latestChatRoomId = chatViewModel.uiState.value.chatList.firstOrNull()?.chatRoomId

                        if (latestChatRoomId != null) {
                            val intent = Intent(context, ChatActivity::class.java).apply {
                                putExtra("chatRoomId", latestChatRoomId)
                            }
                            context.startActivity(intent)
                            (context as? Activity)?.overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.none
                            )
                            coroutineScope.launch {
                                delay(50)
                                navController.navigateChatList()
                            }
                        }
                    },
                    enabled = true
                )
            }
        }
    }
}

@Composable
fun LampSuccessInfo(
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
fun ProfileSuccessInfoList(
    matchSuggestion: MatchSuggestionDomainModel?
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.animateContentSize()
    ) {
        ProfileSuccessInfo(
            url = matchSuggestion?.owner?.profileImageUrls?.get(0),
            text = matchSuggestion?.owner?.name ?: "",
            isOwnerProfile = true
        )

        matchSuggestion?.participants?.forEach { participant ->
            ProfileSuccessInfo(
                url = participant.profileImageUrls.firstOrNull(),
                text = participant.name,
                userId = participant.userId,
                isOwnerProfile = false
            )
        }
    }
}

@Composable
fun ProfileSuccessInfo(
    url: String?,
    text: String,
    userId: Int = 0,
    isOwnerProfile: Boolean,
    onKickButtonClick: (Int, String) -> Unit = { i: Int, s: String -> }
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
            key(url) {
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
            }
        }
        Text(text = text, color = Color.White, style = Typography.normal9)
    }
}

@Composable
fun MatchSuccessShadowCircleBackground(matchSuggestion: MatchSuggestionDomainModel?) {
    // mood에 따라 색상 변경
    val shadowColor = when (matchSuggestion?.color) {
        "FUNNY" -> MoodRed
        "CASUAL" -> MoodYellow
        "SERIOUS" -> MoodBlue
        else -> MoodRed
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Canvas(modifier = Modifier.fillMaxSize()) {
        val size = size
        val center = Offset(size.width / 2, screenHeight.toPx() / 8 * 6)

        val radius = (screenWidth * (250f / 360f)).toPx()
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                isAntiAlias = true
                setShadowLayer(
                    300f,
                    0f,
                    0f,
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
