package com.devndev.lamp.presentation.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun FindLampScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val myInfo by viewModel.myInfo.collectAsState()
    val navOption = navOptions {
        launchSingleTop = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // circle animation
            BreathingAnimation()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(149.dp))
                HomeTextArea(
                    nameText = "트와이수더",
                    middleText = stringResource(id = R.string.matching_header_find_lamp),
                    bottomText = stringResource(id = R.string.matching_sub_header_find_lamp),
                    gender = if (myInfo?.gender == "MALE") {
                        "FEMALE"
                    } else {
                        "MALE"
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 159.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                LampButton(
                    isGradient = true,
                    buttonText = stringResource(id = R.string.find_other_lamp),
                    onClick = {
                        // TODO : MatchingVoteScreen과 화면 연결 필요
//                        navController.navigateVote(navOptions = navOption)
                    },
                    buttonWidth = 190,
                    enabled = true
                )
            }
        }
    }
}

@Composable
fun BreathingAnimation() {
    val transition = rememberInfiniteTransition()
    val animatedBlur by transition.animateFloat(
        initialValue = 50f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Breathing Animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Canvas(
            modifier = Modifier
                .size(5.dp)
                .background(Color.Transparent)
                .align(Alignment.Center)
        ) {
            drawBreathing(
                blur = animatedBlur,
                alpha = 1f,
                center = center
            )
        }
    }
}

fun DrawScope.drawBreathing(blur: Float, alpha: Float, center: Offset) {
    val radius = size.minDimension / 2

    drawIntoCanvas { canvas ->
        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            color = Color.Transparent.toArgb()
            setShadowLayer(
                blur,
                0f,
                0f,
                WomanColor.copy(alpha = alpha + 0.5f).toArgb()
            )
        }
        canvas.nativeCanvas.drawCircle(center.x, center.y, radius + blur, paint)
    }
}
