package com.devndev.lamp.presentation.ui.alarm

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.alarm.AlarmDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.LampButton
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: AlarmViewModel = hiltViewModel()
) {
    BackHandler {
        navController.popBackStack()
    }

    val alarms by viewModel.alarms.collectAsState()

    val inviteList by remember { derivedStateOf { alarms.filter { it.type == "INVITE" } } }
    val visitList by remember { derivedStateOf { alarms.filter { it.type == "VISIT" } } }

    var isInvitationExpanded by remember { mutableStateOf(false) }
    var isVisitExpanded by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(inviteList) {
        if (inviteList.isEmpty() && isInvitationExpanded) {
            isInvitationExpanded = false
        }
    }

    LaunchedEffect(visitList) {
        if (visitList.isEmpty() && isVisitExpanded) {
            isVisitExpanded = false
        }
    }

    val pullRefreshState = rememberPullToRefreshState()

    Column(
        modifier = modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (pullRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                isRefreshing = true
                delay(1500)
                viewModel.getAlarm()
                isRefreshing = false
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullRefreshState.startRefresh()
            } else {
                pullRefreshState.endRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullRefreshState.startRefresh()
            } else {
                pullRefreshState.endRefresh()
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.weight(1f)
        ) {
            TopNavigationBar(
                text = stringResource(id = R.string.notification),
                isNeedXButton = false,
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                if (isRefreshing) {
                    PullToRefreshContainer(
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    item {
                        AlarmSection(
                            title = stringResource(id = R.string.invite_notification),
                            isExpanded = isInvitationExpanded,
                            onToggleExpand = {
                                isInvitationExpanded = !isInvitationExpanded
                            },
                            alarms = inviteList,
                            color = WomanColor,
                            onAcceptClick = {
                                viewModel.acceptInvite(
                                    inviteRequestUserId = it.inviteUserId ?: 0,
                                    alarmId = it.id
                                )
                            },
                            onRejectClick = {
                                viewModel.rejectInvite(
                                    inviteRequestUserId = it.inviteUserId ?: 0,
                                    alarmId = it.id
                                )
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                    item {
                        AlarmSection(
                            title = stringResource(id = R.string.visit_notification),
                            isExpanded = isVisitExpanded,
                            onToggleExpand = {
                                isVisitExpanded = !isVisitExpanded
                            },
                            alarms = visitList,
                            color = ManColor,
                            onAcceptClick = {
                                viewModel.acceptVisit(
                                    lampId = it.lampId,
                                    visitUserId = it.visitUserId ?: 0,
                                    alarmId = it.id
                                )
                            },
                            onRejectClick = {
                                viewModel.rejectVisit(
                                    lampId = it.lampId,
                                    visitUserId = it.visitUserId ?: 0,
                                    alarmId = it.id
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AlarmSection(
    title: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    alarms: List<AlarmDomainModel>,
    color: Color,
    onAcceptClick: (AlarmDomainModel) -> Unit,
    onRejectClick: (AlarmDomainModel) -> Unit
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
            enter = slideInVertically(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.Top) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically(animationSpec = tween(500)) + shrinkVertically() + fadeOut()
        ) {
            Column {
                alarms.forEachIndexed { index, alarmData ->
                    AlarmItem(
                        alarmData = alarmData,
                        onAcceptClick = { onAcceptClick(alarmData) },
                        onRejectClick = { onRejectClick(alarmData) }
                    )
                    if (index < alarms.size - 1) {
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
fun AlarmItem(
    alarmData: AlarmDomainModel,
    onAcceptClick: (AlarmDomainModel) -> Unit,
    onRejectClick: (AlarmDomainModel) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = LampBlack)
            .padding(top = 10.dp, bottom = 15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = getTimeAgo(alarmData.createdAt), style = Typography.normal12, color = Gray3)

        Text(text = alarmData.content, style = Typography.medium18, color = Color.White)

        val buttonText = when (alarmData.type) {
            "INVITE" -> stringResource(id = R.string.accept_invite)
            "VISIT" -> stringResource(id = R.string.accept_visit)
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
                onClick = {
                    onAcceptClick(alarmData)
                },
                buttonWidth = 1,
                enabled = true,
                textStyle = Typography.medium15,
                height = 40
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                modifier = Modifier.height(40.dp),
                onClick = {
                    onRejectClick(alarmData)
                },
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

fun getTimeAgo(isoTime: String): String {
    // Parsing the ISO time string to ZonedDateTime with the original offset
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val zonedDateTime = ZonedDateTime.parse(isoTime, formatter)

    // Getting the current time in Korean Standard Time (KST) explicitly (UTC+9)
    val now = ZonedDateTime.now(ZoneOffset.ofHours(9)).minusHours(9)

    // Calculating the duration between the provided time and now
    val duration = Duration.between(zonedDateTime, now)

    // Converting duration to minutes and hours
    val minutes = duration.toMinutes()
    val hours = duration.toHours()

    // Return appropriate string based on the duration
    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        else -> {
            val days = duration.toDays()
            "${days}일 전"
        }
    }
}
