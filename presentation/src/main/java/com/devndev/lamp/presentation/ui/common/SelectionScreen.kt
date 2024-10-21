package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun SelectionScreen(
    text: String,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
//    val height = LocalConfiguration.current.screenHeightDp.dp * 0.1f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        if (text.isNotEmpty()) {
            Text(
                text = text,
                color = Color.White,
                style = Typography.semiBold25,
                lineHeight = 33.sp,
                textAlign = TextAlign.Center
            )
        }
        content()
    }
}
