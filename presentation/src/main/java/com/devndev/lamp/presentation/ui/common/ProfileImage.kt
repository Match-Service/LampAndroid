package com.devndev.lamp.presentation.ui.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun ProfileImage(
    bitmap: Bitmap?,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    isFirstImage: Boolean = false
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(WomanColor, ManColor)
    )

    val modifier = Modifier
        .size(95.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Gray)
        .then(
            if (isFirstImage) {
                Modifier.border(
                    width = 1.dp,
                    brush = gradientBrush,
                    shape = RoundedCornerShape(4.dp)
                )
            } else {
                Modifier.border(width = 1.dp, color = Gray)
            }
        )
        .clickable(onClick = onClick)

    Box(modifier = modifier) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.image_icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )

            if (isFirstImage) {
                MainProfileImage()
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .background(color = Color.Transparent)
                    .clickable(onClick = onDelete)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.x_button),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else {
            if (isFirstImage) {
                MainProfileImage()
            }
        }
    }
}

@Composable
fun MainProfileImage() {
    Box(
        modifier = Modifier
            .size(width = 41.dp, height = 26.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
            .background(
                color = WomanColor
            )
    ) {
        Text(
            text = stringResource(id = R.string.representative),
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            style = Typography.normal12
        )
    }
}
