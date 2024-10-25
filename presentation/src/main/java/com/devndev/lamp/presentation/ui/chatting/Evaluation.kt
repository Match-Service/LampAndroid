package com.devndev.lamp.presentation.ui.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun Evaluation(/*매칭 정보 필요*/) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ManColor, shape = RoundedCornerShape(15.dp))
            .padding(vertical = 15.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "멋쟁이신사들", color = LampBlack, style = Typography.medium18)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.heart),
                contentDescription = null,
                tint = LampBlack
            )
            Text(text = "이쁜2들", color = LampBlack, style = Typography.medium18)
        }
        Text(
            text = "7월 6일" + stringResource(id = R.string.chat_evaluation),
            color = LampBlack,
            style = Typography.normal12
        )
    }
}

@Preview
@Composable
fun Pre() {
    Evaluation()
}
