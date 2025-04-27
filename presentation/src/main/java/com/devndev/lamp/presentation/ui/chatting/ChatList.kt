package com.devndev.lamp.presentation.ui.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography

@Composable
fun Chat(
    onChatClick: () -> Unit = {},
    chat: ChatRoomDomainModel
) {
    val isChatExist = chat.lastMessageInfo != null
    val isScheduleExist = chat.appointment != null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .clickable { onChatClick() },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ChatTopSection(chat)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = chat.myLampName, color = Color.White, style = Typography.medium18)
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(text = chat.otherLampName, color = Color.White, style = Typography.medium18)
            }

            if (isChatExist) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.width(220.dp),
                        text = chat.lastMessageInfo?.userName
                            ?: (": " + chat.lastMessageInfo?.message)
                            ?: "",
                        color = Color.White,
                        style = Typography.medium15,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "21분 전",
                            color = Gray3,
                            style = Typography.normal9
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    color = ManColor,
                                    shape = RoundedCornerShape(30.dp)
                                )
                                .padding(vertical = 2.dp, horizontal = 6.dp)
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "14",
                                color = Color.White,
                                style = Typography.normal9
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = stringResource(id = R.string.chat_created),
                    color = Gray3,
                    style = Typography.medium15
                )
            }
        }
//        if (isScheduleExist) {
//            Box() {
//                HorizontalDivider(thickness = 1.dp, color = LightGray)
//                Row(
//                    modifier = Modifier.padding(top = 10.dp),
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.calendar),
//                            contentDescription = null,
//                            tint = ManColor
//                        )
//                        Text(text = "D-1", color = ManColor, style = Typography.normal12)
//                    }
//                    Text(
//                        text = chat.appointment?.appointmentPlace,
//                        color = ManColor,
//                        style = Typography.normal12
//                    )
//                }
//            }
//        }
    }
}

@Composable
fun ChatTopSection(chat: ChatRoomDomainModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bulb),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                formatToMonthDay(chat.startDate) + " " + stringResource(id = R.string.lamp_on),
                color = Gray3,
                style = Typography.normal12
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.people_icon),
                contentDescription = null,
                tint = Gray3
            )
            Text(
                "${chat.inviteUserCount + 1} : ${chat.inviteUserCount + 1}",
                color = Gray3,
                style = Typography.normal12
            )
        }
    }
}
