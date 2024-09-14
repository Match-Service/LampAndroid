package com.devndev.lamp.presentation.ui.splsh

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.ui.theme.LampBlack
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {
    PngSequenceSplashAnimation(
        frameCount = 35,
        frameDurationMillis = 33L, // 각 프레임을 33ms (약 30fps)으로 설정
        onAnimationEnd = {
            onSplashEnd() // 애니메이션이 끝나면 콜백
        }
    )
}

@SuppressLint("DefaultLocale", "RememberReturnType", "DiscouragedApi")
@Composable
fun PngSequenceSplashAnimation(
    frameCount: Int, // 전체 프레임 개수
    frameDurationMillis: Long, // 각 프레임의 지속 시간
    onAnimationEnd: () -> Unit // 애니메이션이 끝났을 때의 콜백
) {
    // 현재 프레임을 기억
    var currentFrame by remember { mutableStateOf(0) }
    val context = LocalContext.current

    // 애니메이션 실행
    LaunchedEffect(key1 = true) {
        while (currentFrame < frameCount) {
            delay(frameDurationMillis) // 각 프레임의 지속 시간
            currentFrame++
        }
        delay(500)
        onAnimationEnd() // 애니메이션 끝났을 때 호출
    }

    // 현재 프레임에 맞는 리소스 이름 생성
    val frameName = String.format("splash_%02d", currentFrame) // splash_00 ~ splash_35
    val drawableResourceId = context.resources.getIdentifier(frameName, "drawable", context.packageName)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LampBlack),
        contentAlignment = Alignment.Center
    ) {
        // 이미지 표시
        Image(
            painter = painterResource(id = drawableResourceId),
            contentDescription = "Splash Animation Frame",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }
}
