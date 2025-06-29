package com.devndev.lamp.presentation.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.Typography

@Composable
fun OnBoardingBaseScreen(
    title: String,
    msg: String,
    underlineText: String,
    info: String,
    res: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LampBlack),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = Typography.semiBold25.copy(fontWeight = FontWeight(800)),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = buildAnnotatedString {
                val startIndex = msg.indexOf(underlineText)
                if (startIndex >= 0) {
                    append(msg.substring(0, startIndex))
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    ) {
                        append(underlineText)
                    }
                    append(msg.substring(startIndex + underlineText.length))
                } else {
                    append(msg)
                }
            },
            style = Typography.medium18.copy(
                fontWeight = FontWeight(500),
                letterSpacing = (-0.02f * 18.sp.value).sp
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = info,
            style = Typography.normal12,
            color = LightGray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Box(
            modifier = Modifier
                .height(280.dp)
                .width(161.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .clipToBounds()
        ) {
            Image(
                painter = painterResource(res),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 145.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                LampBlack.copy(alpha = 0f),
                                LampBlack.copy(alpha = 0.5f),
                                LampBlack.copy(alpha = 0.8f),
                                LampBlack,
                                LampBlack
                            )
                        )
                    )
            )
        }
    }
}
