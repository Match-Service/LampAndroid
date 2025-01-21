package com.devndev.lamp.presentation.ui.creation

import androidx.compose.foundation.layout.height
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
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography

@Composable
fun OptionButton(
    optionText: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    width: Int,
    height: Int
) {
    Button(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp),
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Gray,
            contentColor = if (isSelected) LampBlack else Color.White
        ),
        shape = RoundedCornerShape(40.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = optionText,
            style = Typography.medium15
        )
    }
}
