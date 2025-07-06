package com.devndev.lamp.presentation.ui.home.matchinghome

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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import coil.compose.AsyncImage
import com.devndev.lamp.domain.model.lamp.Lamp
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
import com.devndev.lamp.presentation.ui.home.matchinghome.viewmodel.MatchingHomeViewModel
import com.devndev.lamp.presentation.ui.search.navigation.navigateInvite
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun MatchingFailHomeScreen(
    modifier: Modifier,
    navController: NavController,
    updateEvent: SharedFlow<Unit>,
    updateStatus: SharedFlow<String>,
    viewModel: MatchingHomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var kickUserId by remember { mutableIntStateOf(0) }
    var kickUserName by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val navOption = navOptions {
        launchSingleTop = true
    }

    LaunchedEffect(Unit) {
        updateEvent.collect {
            viewModel.getMyLamp()
        }
    }

    LaunchedEffect(Unit) {
        updateStatus.collect { status ->
            when (status) {
                "PREPARE" -> {
                    viewModel.updateIsMatching(false)
                }
                "MATCHING" -> {
                    viewModel.updateIsMatching(true)
                }
            }
        }
    }

    val reMatching = stringResource(id = R.string.re_matching)
    val cancelMatching = stringResource(id = R.string.cancel_matching)
    val matchingFail = stringResource(id = R.string.matching_header_fail)
    val pleaseReMatching = stringResource(id = R.string.matching_sub_header_fail)

    if (!state.isLoading) {
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
                ShadowCircleBackground1()
            }

            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                MatchingHomeTopBar(
//                    onExitIconClick = {
//                        if (state.isOwner) {
//                            isDeletePopupShow = true
//                        } else {
//                            isExitPopupShow = true
//                        }
//                    },
//                    onShareIconClick = {}
//                )
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = matchingFail,
                    color = Color.White,
                    style = Typography.semiBold25.copy(lineHeight = 33.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = pleaseReMatching,
                    color = Color.White,
                    style = Typography.normal12.copy(lineHeight = 16.sp),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 원의 최 상단 부분
                Spacer(modifier = Modifier.height(((screenHeight / 7) * 5) - 250.dp))

//                Spacer(modifier = Modifier.height(70.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        state.myLamp?.lamp?.name?.let {
                            Text(
                                text = it,
                                color = Color.White,
                                style = Typography.semiBold20
                            )
                        }
//                        if (state.isOwner) {
//                            if (!state.isMatching) {
//                                Icon(
//                                    painter = painterResource(
//                                        id = R.drawable.edit_icon
//                                    ),
//                                    contentDescription = null,
//                                    tint = Color.White,
//                                    modifier = Modifier.clickable {
//                                        navController.navigateCreation(isEdit = true, navOptions = navOption)
//                                    }
//                                )
//                            }
//                        }
                    }
                    var mood = ""
                    when (state.myLamp?.lamp?.color) {
                        "FUNNY" -> mood = stringResource(id = R.string.funny_mood)
                        "CASUAL" -> mood = stringResource(id = R.string.casual_mood)
                        "SERIOUS" -> mood = stringResource(id = R.string.serious_mood)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LampFailInfo(
                            painter = painterResource(id = R.drawable.region_icon),
                            text = convertLocation(state.myLamp?.lamp?.location)
                        )
                        LampFailInfo(
                            painter = painterResource(id = R.drawable.people_icon),
                            text = "${state.myLamp?.lamp?.hopeMatchNumber ?: "0"}:${state.myLamp?.lamp?.hopeMatchNumber ?: "0"}"
                        )
                        LampFailInfo(painter = painterResource(id = R.drawable.heart), text = mood)
                    }

                    state.myLamp?.lamp?.description?.let {
                        Text(
                            text = it,
                            modifier = Modifier.width(270.dp),
                            maxLines = 3,
                            style = Typography.normal9,
                            color = Gray3,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    state.myLamp?.let { myLamp ->
                        ProfileFailInfoList(
                            myLamp = myLamp,
                            isOwner = state.isOwner,
                            isMatching = state.isMatching
                        )
                    }

                    Spacer(modifier = Modifier.height(69.dp))

                    LampButton(
                        isGradient = !state.isMatching,
                        buttonWidth = 156,
                        buttonText = reMatching,
                        onClick = {
                            // startMatch 하면 userStatus = MATCHING
                            viewModel.startMatch()
                        },
                        enabled = state.isOwner
                    )
                    LampButton(
                        isGradient = false,
                        buttonWidth = 156,
                        buttonText = cancelMatching,
                        onClick = {
                            viewModel.stopMatch()
                        },
                        enabled = true
                    )
                }
            }
        }
    }
}

@Composable
fun LampFailInfo(
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
fun ProfileFailInfoList(
    myLamp: LampDomainModel,
    isOwner: Boolean,
    isMatching: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.animateContentSize()
    ) {
        ProfileFailInfo(
            url = myLamp.lamp?.owner?.profileImageUrl,
            text = myLamp.lamp?.owner?.name ?: "",
            isOwnerProfile = true,
            isOwner = isOwner,
            isMatching = isMatching
        )

        myLamp.lamp?.participants?.forEach { participant ->
            ProfileFailInfo(
                url = participant.profileImageUrl,
                text = participant.name,
                userId = participant.userId,
                isOwnerProfile = false,
                isOwner = isOwner,
                isMatching = isMatching
            )
        }
    }
}

@Composable
fun ProfileFailInfo(
    url: String?,
    text: String,
    userId: Int = 0,
    isOwnerProfile: Boolean,
    isOwner: Boolean,
    isMatching: Boolean,
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
//            if (isOwnerProfile) {
//                Box() {
//                    Icon(
//                        painter = painterResource(id = R.drawable.owner_icon_out),
//                        contentDescription = null,
//                        tint = Color.Unspecified,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                    Icon(
//                        painter = painterResource(id = R.drawable.owner_icon_in),
//                        contentDescription = null,
//                        tint = Color.Unspecified,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            } else if (isOwner && !isMatching) {
//                Box(
//                    contentAlignment = Alignment.TopEnd,
//                    modifier = Modifier
//                        .size(25.dp)
//                        .clickable(
//                            indication = null,
//                            interactionSource = remember { MutableInteractionSource() }
//                        ) { onKickButtonClick(userId, text) }
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.x_circle_icon),
//                        contentDescription = null,
//                        tint = Color.Unspecified
//                    )
//                }
//            }
        }
        Text(text = text, color = Color.White, style = Typography.normal9)
    }
}

@Composable
fun ShadowCircleBackground1() {
    Canvas(modifier = Modifier
        .fillMaxSize()
    ) {
        val radius = 400.dp.toPx()
        val circleOffset = Offset(center.x, center.y + 300.dp.toPx())
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            color = Color.Transparent.toArgb()
            setShadowLayer(
                200f,
                0f,
                200.dp.toPx(), // yOffset
                Gray.copy(alpha = 1f).toArgb()
            )
        }

        // 회색 원
        drawCircle(
            color = Gray.copy(alpha = 0.9f),
            radius = radius,
            center = circleOffset
        )

        // 그림자
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.apply {
                save()
                drawCircle(center.x, center.y, radius, paint)
                restore()
            }
        }
    }
}
