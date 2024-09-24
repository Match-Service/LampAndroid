package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun LampButtonWithIcon(
    isGradient: Boolean,
    buttonText: String,
    guideButtonText: String = "",
    onClick: () -> Unit,
    icon: Painter,
    onIconClick: () -> Unit = {},
    enabled: Boolean
) {
    val buttonColors = getButtonColor(isGradient = isGradient)
    val buttonModifier = Modifier.buttonBackGround(isGradient = isGradient, true)

    Button(
        onClick = onClick,
        modifier = buttonModifier
            .width(300.dp)
            .height(85.dp),
        colors = buttonColors,
        enabled = enabled
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
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
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.clickable { onIconClick() }
            )
        }
    }
}

@Composable
fun LampButton(
    isGradient: Boolean,
    buttonText: String,
    onClick: () -> Unit,
    buttonWidth: Int = 0,
    enabled: Boolean,
    icon: Painter? = null,
    textStyle: TextStyle = Typography.medium18,
    height: Int = 48
) {
    val buttonColors = getButtonColor(isGradient = isGradient)

    val buttonModifier = if (buttonWidth == 0) {
        Modifier
            .buttonBackGround(isGradient = isGradient, enabled = enabled)
            .fillMaxWidth()
            .height(height.dp)
    } else {
        Modifier.buttonBackGround(isGradient = isGradient, enabled = enabled)
            .height(height.dp)
    }

    Button(
        onClick = onClick,
        modifier = buttonModifier,
        colors = buttonColors,
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = buttonText,
                style = textStyle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun getButtonColor(isGradient: Boolean): ButtonColors {
    return if (isGradient) {
        ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContentColor = LightGray,
            disabledContainerColor = Gray
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Gray,
            contentColor = Color.White,
            disabledContainerColor = Gray,
            disabledContentColor = Color.White
        )
    }
}

@Composable
fun Modifier.buttonBackGround(isGradient: Boolean, enabled: Boolean): Modifier {
    return this.then(
        if (enabled) {
            if (isGradient) {
                Modifier.background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(WomanColor, ManColor)
                    ),
                    shape = RoundedCornerShape(80.dp)
                )
            } else {
                Modifier.background(
                    color = Gray,
                    shape = RoundedCornerShape(80.dp)
                )
            }
        } else {
            Modifier.background(
                color = Gray,
                shape = RoundedCornerShape(80.dp)
            )
        }
    )
}

@Composable
fun CheckButton(size: Int, selected: Boolean, onClick: () -> Unit) {
    val borderColor = if (selected) Color.White else Gray
    val innerColor = if (selected) Color.White else Color.Transparent
    val iconColor = if (selected) Color.Black else Gray3

    Box(
        modifier = Modifier
            .size(size.dp)
            .background(color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(size.dp / 2)
                .background(color = innerColor, shape = CircleShape)
                .align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.radio_button_check),
                contentDescription = "Check",
                tint = iconColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
