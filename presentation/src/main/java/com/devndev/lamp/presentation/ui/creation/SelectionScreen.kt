package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun SelectionScreen(
    text: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = Color.White,
                style = Typography.semiBold25,
                lineHeight = 33.sp,
                textAlign = TextAlign.Center
            )
            content()
        }
    }
}

@Composable
fun OptionButton(optionText: String, isSelected: Boolean, onSelect: () -> Unit) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Gray,
            contentColor = if (isSelected) Color.Black else Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(bottom = 4.dp)
    ) {
        Text(text = optionText, style = Typography.medium15)
    }
}
