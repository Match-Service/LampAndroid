package com.devndev.lamp.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.UserDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun CircleProfile(profile: UserDomainModel, onDeleteButtonClick: () -> Unit, isCanDelete: Boolean) {
    val thumbnail = if (profile.thumbnail == "") {
        painterResource(id = R.drawable.testimage)
    } else {
        rememberAsyncImagePainter(model = profile.thumbnail)
    }
    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = thumbnail,
                contentDescription = "thunmnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Text(text = profile.name, style = Typography.normal9, color = Gray3)
        }
        if (isCanDelete) {
            Icon(
                painter = painterResource(id = R.drawable.circle_x_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable { onDeleteButtonClick() },
                tint = Color.Unspecified
            )
        }
    }
}
