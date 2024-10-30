package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun TopNavigationBar(
    text: String,
    isNeedXButton: Boolean = true,
    horizontalPadding: Int = 0,
    onBackButtonClick: () -> Unit,
    onXButtonClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = R.drawable.back_arrow),
                contentDescription = "뒤로가기",
                tint = Color.White,
                modifier = Modifier.clickable {
                    onBackButtonClick()
                }
            )
            Text(
                text = text,
                style = Typography.semiBold25,
                fontSize = 25.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        if (isNeedXButton) {
            Icon(
                painter = painterResource(id = R.drawable.x_button_big),
                contentDescription = "나가기",
                tint = Color.White,
                modifier = Modifier.clickable { onXButtonClick() }
            )
        }
    }
}