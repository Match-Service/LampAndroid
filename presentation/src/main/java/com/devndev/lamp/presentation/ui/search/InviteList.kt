package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun InviteList(
    profileList: List<Item>,
    onCheckedItemChanged: (Item, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recent_friend),
            style = Typography.normal12,
            color = Gray3
        )
        LazyColumn {
            items(profileList.size) { index ->
                val profile = profileList[index]
                var isSelected by remember { mutableStateOf(false) }

                InviteItem(
                    profile = profile,
                    selected = isSelected,
                    onCheckedChange = {
                        isSelected = it
                        onCheckedItemChanged(profile, it)
                    }
                )
                if (index < profileList.size - 1) {
                    HorizontalDivider(
                        color = Gray3.copy(alpha = 0.3f),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}

@Composable
fun InviteItem(profile: Item, selected: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.testimage),
                    contentDescription = "testimage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                NameSpace(profile = profile)
            }
            InviteIcon(
                selected = selected,
                onClick = { onCheckedChange(!selected) }
            )
        }
    }
}

@Composable
fun InviteIcon(selected: Boolean, onClick: () -> Unit) {
    val size = 20.dp
    val borderColor = if (selected) Color.White else Gray
    val innerColor = if (selected) Color.White else Color.Transparent
    val iconColor = if (selected) Color.Black else Gray3

    Box(
        modifier = Modifier
            .size(size)
            .background(color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(size / 2)
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
