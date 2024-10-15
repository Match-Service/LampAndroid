package com.devndev.lamp.presentation.ui.review

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun LampReviewScreen() {
    val tmpLampName = "이쁜2들"
    val tmpLampDate = "7월 6일"

    SelectionScreen(text = "$tmpLampName ${stringResource(id = R.string.review_name)}") {
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "$tmpLampDate, 즐거웠던 만큼 밝기를 올려주세요",
            style = Typography.medium12,
            color = Color.White
        )
    }
}
