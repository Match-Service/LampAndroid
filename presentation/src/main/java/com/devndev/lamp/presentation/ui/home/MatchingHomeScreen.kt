package com.devndev.lamp.presentation.ui.home

import android.media.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.TempDB
import com.devndev.lamp.presentation.ui.creation.navigation.navigateCreation
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun MatchingHomeScreen(modifier: Modifier, navController: NavController) {
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
                onExitIconClick = {
                    TempStatus.updateIsMatching(false)
                    TempDB.personnel = ""
                    TempDB.region = ""
                    TempDB.mood = ""
                    TempDB.lampName = ""
                    TempDB.lampSummary = ""
                },
                onShareIconClick = {}
            )
            Text(
                text = stringResource(id = R.string.matching_header_invite),
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

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = TempDB.lampName, color = Color.White, style = Typography.semiBold20)
                    Icon(
                        painter = painterResource(
                            id = R.drawable.edit_icon
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.navigateCreation()
                        }
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LampInfo(
                        painter = painterResource(id = R.drawable.region_icon),
                        text = TempDB.region
                    )
                    LampInfo(
                        painter = painterResource(id = R.drawable.people_icon),
                        text = TempDB.personnel
                    )
                    LampInfo(painter = painterResource(id = R.drawable.heart), text = "신나는 분위기")
                }

                Text(
                    text = TempDB.lampSummary,
                    modifier = Modifier.width(270.dp),
                    maxLines = 3,
                    style = Typography.normal9,
                    color = Gray3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))

                ProfileInfo(null, "북창동루쥬라")

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Gray,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.invite_friend),
                        color = Color.White,
                        style = Typography.medium18,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LampInfo(
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
fun ProfileInfoList() {
    // todo 추후 친구 초대 가능할 경우 프로필 이미지들 Row로 확장
}

@Composable
fun ProfileInfo(
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
        Text(text = text, color = Gray3, style = Typography.normal9)
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
