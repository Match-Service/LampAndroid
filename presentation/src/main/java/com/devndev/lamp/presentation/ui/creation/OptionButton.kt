package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun OptionButton(optionText: String, isSelected: Boolean, onSelect: () -> Unit) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Gray,
            contentColor = if (isSelected) LampBlack else Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(bottom = 4.dp)
    ) {
        Text(text = optionText, style = Typography.medium15)
    }
}
