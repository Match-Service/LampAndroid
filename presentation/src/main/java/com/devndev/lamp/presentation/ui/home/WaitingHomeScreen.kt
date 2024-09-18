package com.devndev.lamp.presentation.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.LampButtonWithIcon
import com.devndev.lamp.presentation.ui.theme.WomanColor
import kotlinx.coroutines.delay

@Composable
fun WaitingHomeScreen(modifier: Modifier, navController: NavController) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val profileName by TempStatus.profileName.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BreathingCircleAnimation()
//        GradientBackground(animationProgress = animationProgress)
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            ) {
                HomeTextArea(
                    nameText = "북창동루쥬라 " + stringResource(id = R.string.sir),
                    middleText = stringResource(id = R.string.waiting_header),
                    bottomText = profileName + stringResource(id = R.string.waiting_guide)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(85.dp))
                LampButtonWithIcon(
                    isGradient = false,
                    buttonText = stringResource(id = R.string.cancel_waiting),
                    onClick = {},
                    icon = painterResource(id = R.drawable.x_button_big),
                    onIconClick = {
                        TempStatus.updateIsWaiting(false)
                        TempStatus.updateProfileName("")
                    },
                    enabled = false
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun GradientBackground(animationProgress: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxRadius = size.minDimension / 2f
        val radius = (maxRadius * animationProgress).coerceAtLeast(1f)
        val center = Offset(size.width / 2, size.height / 2)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(WomanColor.copy(alpha = 0.7f), Color.Transparent),
                center = center,
                radius = radius
            ),
            radius = radius
        )
    }
}

@Composable
fun BreathingCircleAnimation() {
    val transitionDuration = 3000

    var isEnlarged by remember { mutableStateOf(true) }

    val animateAlpha by animateFloatAsState(
        targetValue = if (isEnlarged) 0.5f else 0.1f,
        animationSpec = tween(
            durationMillis = transitionDuration,
            delayMillis = 0,
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
        while (true) {
            isEnlarged = !isEnlarged
            delay(transitionDuration.toLong())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent) // 배경색 투명
    ) {
        Canvas(
            modifier = Modifier
                .size(5.dp)
                .background(Color.Transparent)
                .align(Alignment.Center)
        ) {
            drawBreathingCircle(
                blur = 250f,
                alpha = animateAlpha,
                center = center
            )
        }
    }
}

fun DrawScope.drawBreathingCircle(blur: Float, alpha: Float, center: Offset) {
    val radius = 150f

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
        canvas.nativeCanvas.drawCircle(center.x, center.y, radius, paint)
    }
}
