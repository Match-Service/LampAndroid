package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun LampButton(isGradient: Boolean, buttonText: String, guideButtonText: String = "") {
    val buttonColors = if (isGradient) {
        ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Gray,
            contentColor = Color.White
        )
    }

    val buttonModifier = Modifier
        .then(
            if (isGradient) {
                Modifier.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            WomanColor,
                            ManColor
                        )
                    ),
                    shape = RoundedCornerShape(80.dp)
                )
            } else {
                Modifier
            }
        )

    Button(
        onClick = { /* Do something */ },
        modifier = buttonModifier,
        colors = buttonColors
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column() {
                Text(
                    text = buttonText,
                    fontSize = 18.sp,
                    style = Typography.medium18,
                    textAlign = TextAlign.Center
                )
                if (guideButtonText != "") {
                    Text(
                        text = guideButtonText,
                        modifier = Modifier.padding(top = 8.dp),
                        style = Typography.normal12,
                        fontSize = 12.sp
                    )
                }
            }
            Image(painter = painterResource(id = R.drawable.arrow), contentDescription = "arrow")
        }
    }
}

@Preview
@Composable
fun A() {
    LampButton(true, "램프 생성하기", "램프를 만들고 만남을 시작해 보세요")
}
