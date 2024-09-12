package com.devndev.lamp.presentation.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun MatchingHomeScreen(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack)
    ) {
        ShadowCircleBackground()
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
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            MatchingHomeTopBar(
                onExitIconClick = { TempStatus.updateIsMatching(false) },
                onShareIconClick = {}
            )
            Text(
                text = context.getString(R.string.matching_header_invite),
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

            Text(text = "임시 방 제목", color = Color.White, style = Typography.semiBold20)
        }
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

@Composable
fun ShadowCircleBackground(mood: String = "") {
    // todo mood에 따라 색상 변경

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Canvas(modifier = Modifier.fillMaxSize()) {
        val size = size
        val shadowRadius = 50.dp.toPx()
        val shadowColor = ManColor.copy(alpha = 0.7f)
        val center = Offset(size.width / 2, screenHeight.toPx() / 7 * 5)
        val radius = 250.dp.toPx()
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
    }
}
