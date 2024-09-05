package com.devndev.lamp.presentation.ui.creation

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.MoodBlack
import com.devndev.lamp.presentation.ui.theme.MoodBlue
import com.devndev.lamp.presentation.ui.theme.MoodGray
import com.devndev.lamp.presentation.ui.theme.MoodRed
import com.devndev.lamp.presentation.ui.theme.MoodYellow
import com.devndev.lamp.presentation.ui.theme.Typography
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnrememberedMutableState")
@Composable
fun MoodScreen(selectedOption: String, onSelectOption: (String) -> Unit) {
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val pagerState = rememberPagerState()

    // 페이지를 스와이프할 때마다 회전 각도를 업데이트합니다.
    var rotationAngle by remember { mutableStateOf(0f) }
    val currentPage by derivedStateOf { pagerState.currentPage }
    val pageOffset by derivedStateOf { pagerState.currentPageOffset }

    // 스와이프할 때 애니메이션을 적용합니다.
    LaunchedEffect(pagerState.currentPageOffset) {
        rotationAngle = (currentPage + pageOffset) * -90f + 45f
    }

    // 페이지별 문구 설정
    val pageTexts = listOf(
        "옆으로 넘겨 분위기를\n선택해 주세요",
        "신나는 분위기",
        "캐주얼한 분위기",
        "진지한 분위기"
    )

    // 페이지 컬러랑 불투명도 설정
    val pageColors = listOf(
        MoodBlack.copy(alpha = 0.2f),
        MoodRed.copy(alpha = 0.7f),
        MoodYellow.copy(alpha = 0.7f),
        MoodBlue.copy(alpha = 0.7f)
    )

    // 페이지별 텍스트 크기 설정
    val pageTextSizes = listOf(
        15.sp,
        25.sp,
        25.sp,
        25.sp
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF151515))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.1f),
                    text = context.getString(R.string.select_mood),
                    color = Color.White,
                    style = Typography.semiBold25,
                    lineHeight = 33.sp,
                    textAlign = TextAlign.Center
                )
            }


            // 중앙에 회전하는 원을 배치합니다.
            RotatingCircle(
                rotationAngle = rotationAngle,
                shadowColor = pageColors[pagerState.currentPage], // 현재 페이지에 따라 쉐도우 색상 설정
                screenWidth,
                screenHeight
            )

            // 화면 아래쪽에 스와이프 가능한 페이지를 배치합니다.
            HorizontalPager(
                count = pageColors.size, // 페이지 수
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, rotation ->
                            rotationAngle += rotation
                        }
                    }
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = pageTexts[page],
                        color = pageColors[page],
                        fontSize = pageTextSizes[page],
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset(y = screenHeight - (screenWidth * 0.5f))
                    )
                }
            }
        }
    }
}

@Composable
fun RotatingCircle(rotationAngle: Float, shadowColor: Color, screenWidth: Dp, screenHeight: Dp) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(screenWidth * 1.5f)
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
        ) {
            drawRotatingCircle(rotationAngle, shadowColor, screenWidth, screenHeight)
        }
    }
}

/***
 * 회전하는 원 그리기
 */
fun DrawScope.drawRotatingCircle(rotationAngle: Float, shadowColor: Color, screenWidth: Dp, screenHeight: Dp) {
    val radius = (screenWidth * 1.5f).toPx() / 2
    val centerOffset = Offset(x = size.width / 2, y = screenHeight.toPx())

    // 사분면 색상
    val colors = listOf(
        MoodYellow.copy(alpha = 0.7f),
        MoodBlue.copy(alpha = 0.7f),
        MoodBlack.copy(alpha = 0.2f),
        MoodRed.copy(alpha = 0.7f)
    )

    // 드롭 쉐도우 효과를 적용합니다.
    val shadowOffset = 30f // X, Y 오프셋
    val blurRadius = 100f // 블러 반경

    // 원을 4등분하여 각 사분면에 색상을 적용
    colors.forEachIndexed { index, color ->
        rotate(degrees = index * 90f + rotationAngle, pivot = centerOffset) {
            // 그림자 효과를 그립니다.
            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    setShadowLayer(blurRadius, shadowOffset, shadowOffset, color.toArgb())
                }
                canvas.nativeCanvas.drawArc(
                    centerOffset.x - radius, // left
                    centerOffset.y - radius, // top
                    centerOffset.x + radius, // right
                    centerOffset.y + radius, // bottom
                    0f, 90f, true, paint
                )
            }

            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius),
                size = Size(radius * 2, radius * 2)
            )
        }
    }

    // 검정색 원 그리기
    drawCircle(
        color = MoodGray,
        radius = radius,
        center = centerOffset
    )
}