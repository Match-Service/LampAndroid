package com.devndev.lamp.presentation.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.CheckButton

@Composable
fun InviteList(
    searchUserList: List<UserDomainModel>,
    recentUserList: List<UserDomainModel>,
    selectedItems: List<UserDomainModel>,
    onCheckedItemChanged: (UserDomainModel, Boolean) -> Unit,
    myName: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        items(searchUserList.size) { index ->
            val profile = searchUserList[index]

            val isSelected = selectedItems.any { it.id == profile.id }

            InviteItem(
                profile = profile,
                selected = isSelected,
                onCheckedChange = {
                    onCheckedItemChanged(profile, it)
                },
                myName = myName
            )
            if (index < searchUserList.size - 1) {
                HorizontalDivider(
                    color = Gray3.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
            }
        }

        if (recentUserList.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
            item {
                Text(
                    text = stringResource(id = R.string.recent_friend),
                    style = Typography.normal12,
                    color = Gray3
                )
            }
        }

        items(recentUserList.size) { index ->
            val profile = recentUserList[index]

            val isSelected = selectedItems.any { it.id == profile.id }

            InviteItem(
                profile = profile,
                selected = isSelected,
                onCheckedChange = {
                    onCheckedItemChanged(profile, it)
                },
                myName = myName
            )
            if (index < recentUserList.size - 1) {
                HorizontalDivider(
                    color = Gray3.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
            }
        }
    }
}

@Composable
fun InviteItem(
    profile: UserDomainModel,
    selected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    myName: String
) {
    val thumbnail = if (profile.thumbnail == "") {
        painterResource(id = R.drawable.testimage)
    } else {
        rememberAsyncImagePainter(model = profile.thumbnail)
    }
    val lampStatus = profile.lampStatus
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (lampStatus == "OK") {
                        onCheckedChange(!selected)
                    }
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = thumbnail,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                if (lampStatus == "OK") {
                    Text(
                        text = profile.name,
                        style = Typography.medium18,
                        color = Color.White
                    )
                } else {
                    Column() {
                        Text(
                            text = profile.name,
                            style = Typography.medium18,
                            color = Color.White
                        )
                        var lampStatusText = ""
                        when (lampStatus) {
                            "INVITED" ->
                                lampStatusText =
                                    stringResource(id = R.string.lamp_status_invited)

                            "PARTICIPATED" ->
                                lampStatusText =
                                    stringResource(id = R.string.lamp_status_my_lamp, myName)

                            "JOINED" ->
                                lampStatusText =
                                    stringResource(id = R.string.lamp_status_other_lamp)
                        }
                        Text(
                            text = lampStatusText,
                            style = Typography.normal12,
                            color = WomanColor
                        )
                    }
                }
            }
            if (lampStatus == "OK") {
                CheckButton(
                    size = 20,
                    selected = selected,
                    onClick = { onCheckedChange(!selected) }
                )
            }
        }
    }
}
