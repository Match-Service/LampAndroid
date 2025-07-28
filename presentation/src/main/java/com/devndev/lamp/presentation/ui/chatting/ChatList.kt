package com.devndev.lamp.presentation.ui.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import com.devndev.lamp.domain.model.chat.MessageType
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.alarm.getTimeAgo
import com.devndev.lamp.presentation.utils.DateFormatUtil

@Composable
fun Chat(
    modifier: Modifier,
    onChatClick: () -> Unit = {},
    chat: ChatRoomDomainModel,
    gender: String
) {
    val isChatExist = chat.lastMessageInfo != null
    val isAppointmentExist = chat.appointment != null

    val color = if (gender == "MALE") {
        ManColor
    } else {
        WomanColor
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .border(
                width = 1.dp,
                color = LightGray,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable { onChatClick() }
            .padding(vertical = 15.dp, horizontal = 20.dp),
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
                    val text = if (chat.lastMessageInfo?.messageTypeEnum == MessageType.MESSAGE) {
                        "${chat.lastMessageInfo?.userName} : ${chat.lastMessageInfo?.message}"
                    } else {
                        "${chat.lastMessageInfo?.message}"
                    }

                    Text(
                        modifier = Modifier.width(220.dp),
                        text = text,
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
                            text = getTimeAgo(chat.lastMessageInfo?.createdAt ?: ""),
                            color = Gray3,
                            style = Typography.normal9
                        )
                        val unreadMessageCount = if (chat.unreadMessageCount > 0) {
                            chat.unreadMessageCount.toString()
                        } else {
                            ""
                        }
                        if (unreadMessageCount.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = color,
                                        shape = RoundedCornerShape(30.dp)
                                    )
                                    .padding(vertical = 2.dp, horizontal = 6.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = unreadMessageCount,
                                    color = Color.White,
                                    style = Typography.normal9
                                )
                            }
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
            if (isAppointmentExist) {
                val appointmentTime = chat.appointment!!.appointmentDate
                val dateTime =
                    DateFormatUtil.formatIsoToKoreanDate(appointmentTime).split(" ", limit = 2)[1]

                HorizontalDivider(
                    modifier = Modifier.padding(top = 5.dp),
                    thickness = 1.dp,
                    color = LightGray
                )
                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(11.dp),
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = DateFormatUtil.formatToDDay(appointmentTime),
                        color = color,
                        style = Typography.normal12
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "$dateTime ${chat.appointment!!.appointmentPlace}",
                        color = color,
                        style = Typography.normal12
                    )
                }
            }
        }
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
