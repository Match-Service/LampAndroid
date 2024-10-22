package com.devndev.lamp.presentation.ui.notification

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.navigation.navigateMain
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.Gray3
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun NotificationScreen(
    modifier: Modifier,
    navController: NavController,
    pagerState: PagerState,
    isFromMain: Boolean
) {
    BackHandler {
        if (isFromMain) {
            navController.navigateMain(pagerState.currentPage)
        } else {
            navController.popBackStack()
        }
    }
    var isInvitationExpanded by remember { mutableStateOf(false) }
    var isVisitExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.back_arrow),
                    contentDescription = "뒤로가기",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        if (isFromMain) {
                            navController.navigateMain(pagerState.currentPage)
                        } else {
                            navController.popBackStack()
                        }
                    }
                )
                Text(
                    text = stringResource(id = R.string.notification),
                    style = Typography.semiBold25,
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                item {
                    NotificationSection(
                        title = stringResource(id = R.string.invite_notification),
                        isExpanded = isInvitationExpanded,
                        onToggleExpand = { isInvitationExpanded = !isInvitationExpanded },
                        notifications = listOf(
                            NotificationData(
                                hostName = "네네",
                                userName = "북창동루쥬라",
                                roomName = "복창동루루캠프",
                                timeAgo = "36분 전"
                            ),
                            NotificationData(
                                hostName = "북창동김수환",
                                userName = "사용자B",
                                roomName = "캠프B",
                                timeAgo = "15시간 전"
                            )
                        ),
                        color = WomanColor,
                        type = NotificationType.INVITE
                    )
                }
                item { Spacer(modifier = Modifier.height(70.dp)) }
                item {
                    NotificationSection(
                        title = stringResource(id = R.string.visit_notification),
                        isExpanded = isVisitExpanded,
                        onToggleExpand = { isVisitExpanded = !isVisitExpanded },
                        notifications = listOf(
                            NotificationData(
                                hostName = "",
                                userName = "북창동이어진",
                                roomName = "맛쟁이 신사들",
                                timeAgo = "15분 전"
                            ),
                            NotificationData(
                                hostName = "",
                                userName = "사용자B",
                                roomName = "캠프B",
                                timeAgo = "3시간 전"
                            )
                        ),
                        color = ManColor,
                        type = NotificationType.VISIT
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationSection(
    title: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    notifications: List<NotificationData>,
    color: Color,
    type: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onToggleExpand()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            StartCircle(color = color)
            Text(text = title, style = Typography.semiBold20, color = color)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = if (isExpanded) {
                    painterResource(id = R.drawable.reduce_icon)
                } else {
                    painterResource(id = R.drawable.expand_icon)
                },
                contentDescription = null,
                tint = Color.White
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.Top) + fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically(animationSpec = tween(500)) + shrinkVertically() + fadeOut()
        ) {
            Column {
                notifications.forEachIndexed { index, notificationData ->
                    NotificationItem(notificationData = notificationData, type = type)
                    if (index < notifications.size - 1) {
                        HorizontalDivider(
                            color = Gray3.copy(alpha = 0.3f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notificationData: NotificationData, type: Int) {
    Column(
        modifier = Modifier
            .background(color = LampBlack)
            .padding(top = 10.dp, bottom = 15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = notificationData.timeAgo, style = Typography.normal12, color = Gray3)
        val notificationText = when (type) {
            NotificationType.INVITE -> "${notificationData.hostName}님이 ${notificationData.userName}님을\n초대했어요"
            NotificationType.VISIT -> "${notificationData.userName}님이 '${notificationData.roomName}'\n에 참여하기를 원해요"
            else -> ""
        }
        Text(text = notificationText, style = Typography.medium18, color = Color.White)

        val buttonText = when (type) {
            NotificationType.INVITE -> stringResource(id = R.string.accept_invite)
            NotificationType.VISIT -> stringResource(id = R.string.accept_visit)
            else -> ""
        }
        Row(
            modifier = Modifier.padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            LampButton(
                isGradient = true,
                buttonText = buttonText,
                onClick = {},
                buttonWidth = 1,
                enabled = true,
                textStyle = Typography.medium15,
                height = 40
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.height(40.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(id = R.string.reject), style = Typography.medium15)
            }
        }
    }
}

@Composable
fun StartCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(10.dp)
            .background(Color.Transparent)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = color,
                radius = size.minDimension / 2
            )
        }
    }
}

data class NotificationData(
    val hostName: String,
    val userName: String,
    val roomName: String,
    val timeAgo: String
)

object NotificationType {
    const val INVITE = 0
    const val VISIT = 1
}
