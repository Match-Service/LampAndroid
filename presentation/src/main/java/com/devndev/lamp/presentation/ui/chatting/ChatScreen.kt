package com.devndev.lamp.presentation.ui.chatting

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.MessageType
import com.devndev.lamp.domain.model.chat.UserInfo
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.theme.getMainColor
import com.devndev.lamp.presentation.ui.appointment.navigation.navigateAppointment
import com.devndev.lamp.presentation.ui.common.AppointmentStatus
import com.devndev.lamp.presentation.ui.common.ProfilePopup
import com.devndev.lamp.presentation.utils.DateFormatUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatRoomId: Int,
    viewModel: ChatViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context as? Activity

    var isProfilePopupShow by remember { mutableStateOf(false) }
    var selectedUserInfo by remember { mutableStateOf<UserInfo?>(null) }

    var textFieldHeight by remember { mutableStateOf(0) }

    val color = if (state.getAppointmentStatus() == AppointmentStatus.EMPTY_APPOINTMENT) {
        Color.White
    } else {
        getMainColor(state.myInfo?.gender ?: "MALE")
    }

    LaunchedEffect(Unit) {
        viewModel.addChatListener(chatRoomId)
        viewModel.getAppointment(chatRoomId)
    }

    BackHandler {
        activity?.finish()
        activity?.overridePendingTransition(R.anim.none, R.anim.slide_out_right)
    }

    val lazyListState = rememberLazyListState()

    val isAtBottom = remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val totalItemsCount = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1

            lastVisibleItemIndex >= totalItemsCount - 2
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isAtBottom.value }
            .distinctUntilChanged()
            .collect { atBottom ->
                viewModel.setAtBottom(atBottom)

                if (atBottom) {
                    viewModel.setShowNewMessageBadge(false)
                }
            }
    }

    LaunchedEffect(state.needScrollDown) {
        if (state.needScrollDown) {
            lazyListState.scrollToItem(state.chatItems.size)
            viewModel.setNeedScrollDown(false)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchChatData(lastMessageId = null, chatRoomId = chatRoomId)
        delay(300)
        lazyListState.scrollToItem(state.chatItems.size)
    }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->
            if (index == 0) {
                val firstVisibleMessageId = state.chatItems.getOrNull(index)?.message?.id

                if (firstVisibleMessageId != null) {
                    viewModel.fetchChatData(
                        lastMessageId = firstVisibleMessageId,
                        chatRoomId = chatRoomId,
                        onPrependComplete = { newItemCount ->
                            if (newItemCount > 0) {
                                coroutineScope.launch {
                                    lazyListState.scrollToItem(
                                        index = newItemCount,
                                        scrollOffset = offset
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    var currentMessage by remember { mutableStateOf("") }

    if (isProfilePopupShow) {
        ProfilePopup(userInfo = selectedUserInfo) {
            isProfilePopupShow = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ChatTopBar(
            chat = state,
            onBackClick = {
                activity?.finish()
                activity?.overridePendingTransition(R.anim.none, R.anim.slide_out_right)
            },
            onCalendarClick = {
                navController.navigateAppointment()
            },
            color = color,
            appointmentStatus = state.getAppointmentStatus()
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = lazyListState,
            userScrollEnabled = !state.isLoading
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(Gray)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = formatDate(state.chatInfo?.startDate),
                            color = Color.White,
                            style = Typography.normal12
                        )
                    }
                }
                if (!state.isLoading && state.chatMessage.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = R.string.chat_created) + "\n" + stringResource(id = R.string.say_hi),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = Typography.normal12
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigateAppointment()
                            },
                            text = buildAnnotatedString {
                                val text = stringResource(id = R.string.register_appointment)
                                val startIndex = text.indexOf(text)
                                val endIndex = startIndex + text.length
                                append(text)

                                addStyle(
                                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                                    start = startIndex,
                                    end = endIndex
                                )
                            },
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            items(state.chatItems) { chat ->
                ChatBubble(
                    message = chat.message,
                    userInfo = chat.userInfo,
                    isMine = (chat.userInfo.userId == state.myInfo?.userId),
                    onProfileClick = {
                        selectedUserInfo = it
                        isProfilePopupShow = true
                    },
                    onCreateAppointChatClick = {
                        if (state.getAppointmentStatus() != AppointmentStatus.CONFIRM_APPOINTMENT) {
                            navController.navigateAppointment()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(color = Gray)
                .padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = 20.dp)
                .onSizeChanged { size ->
                    textFieldHeight = size.height
                }
        ) {
            BasicTextField(
                modifier = Modifier
                    .background(Gray)
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(
                            if ((textLayoutResult?.lineCount ?: 1) > 1) 5.dp else 27.dp
                        )
                    )
                    .border(
                        1.dp,
                        LightGray,
                        RoundedCornerShape(
                            if ((textLayoutResult?.lineCount ?: 1) > 1) 5.dp else 27.dp
                        )
                    )
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                value = currentMessage,
                onValueChange = { currentMessage = it },
                textStyle = Typography.medium15.copy(color = Gray3),
                singleLine = false,
                maxLines = 6,
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (currentMessage.isNotBlank()) {
                            viewModel.sendChat(chatRoomId, currentMessage)
                            currentMessage = ""
                        }
                    }
                ),
                onTextLayout = {
                    textLayoutResult = it
                }
            ) { innerTextField ->
                if (currentMessage.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.input_chat_message),
                        color = Gray3,
                        style = Typography.medium15
                    )
                }
                innerTextField()
            }

            Box(
                modifier = Modifier
                    .height(30.dp)
                    .background(
                        brush = if (currentMessage.isNotBlank()) {
                            Brush.horizontalGradient(colors = listOf(WomanColor, ManColor))
                        } else {
                            Brush.horizontalGradient(colors = listOf(LightGray, LightGray))
                        },
                        shape = RoundedCornerShape(31.dp)
                    )
                    .clickable(onClick = {
                        if (currentMessage.isNotBlank()) {
                            viewModel.sendChat(chatRoomId, currentMessage)
                            currentMessage = ""
                        }
                    })
                    .padding(horizontal = 14.dp, vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                val textColor = if (currentMessage.isNotBlank()) {
                    Color.White
                } else {
                    Gray3
                }

                Text(
                    text = stringResource(id = R.string.send),
                    color = textColor,
                    style = Typography.normal12
                )
            }
        }
    }

    val textFieldHeightDp = with(LocalDensity.current) { textFieldHeight.toDp() }

    if (state.showNewMessageBadge) {
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(WomanColor, ManColor)
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = textFieldHeightDp + 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp))
                    .background(brush = gradientBrush)
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .clickable {
                        viewModel.setNeedScrollDown(true)
                        viewModel.setShowNewMessageBadge(false)
                    },
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.new_message),
                    color = Color.White,
                    style = Typography.medium15
                )
                Icon(
                    painter = painterResource(R.drawable.expand_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessageDomainModel,
    userInfo: UserInfo,
    isMine: Boolean,
    onProfileClick: (UserInfo) -> Unit = {},
    onCreateAppointChatClick: () -> Unit = {}
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(WomanColor, ManColor)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = when (message.messageTypeEnum) {
            MessageType.MESSAGE -> {
                if (isMine) Arrangement.End else Arrangement.Start
            }

            else -> {
                Arrangement.Center
            }
        }

    ) {
        when (message.messageTypeEnum) {
            MessageType.MESSAGE -> {
                if (isMine) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = formatChatDate(message.createdAt),
                            color = LightGray,
                            style = Typography.normal9
                        )
                        Box(
                            modifier = Modifier
                                .widthIn(max = 206.dp)
                                .background(
                                    color = Gray,
                                    shape = RoundedCornerShape(
                                        topStart = 15.dp,
                                        bottomEnd = 15.dp,
                                        bottomStart = 15.dp
                                    )
                                )
                                .padding(vertical = 7.dp, horizontal = 10.dp)
                        ) {
                            Text(
                                text = message.message,
                                color = Color.White,
                                style = Typography.normal14
                            )
                        }
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        val image = rememberAsyncImagePainter(userInfo.profileImages[0])
                        Image(
                            painter = image,
                            contentDescription = "profileImage",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onProfileClick(userInfo)
                                }
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                val color = if (userInfo.gender == "MALE") {
                                    ManColor
                                } else {
                                    WomanColor
                                }
                                Text(
                                    text = userInfo.name,
                                    color = color,
                                    style = Typography.normal12
                                )
                                Box(
                                    modifier = Modifier
                                        .widthIn(max = 206.dp)
                                        .background(
                                            color = LightGray,
                                            shape = RoundedCornerShape(
                                                topEnd = 15.dp,
                                                bottomEnd = 15.dp,
                                                bottomStart = 15.dp
                                            )
                                        )
                                        .padding(vertical = 7.dp, horizontal = 10.dp)
                                ) {
                                    Text(
                                        text = message.message,
                                        color = Color.White,
                                        style = Typography.normal14
                                    )
                                }
                            }
                            Text(
                                text = formatChatDate(message.createdAt),
                                color = LightGray,
                                style = Typography.normal9
                            )
                        }
                    }
                }
            }

            MessageType.RETRY_APPOINTMENT,
            MessageType.CREATE_APPOINTMENT -> {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush = gradientBrush,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .clickable {
                            onCreateAppointChatClick()
                        }
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = message.message,
                            color = Color.White,
                            style = Typography.medium15
                        )
                        Icon(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = null,
                            modifier = Modifier.size(10.dp),
                            tint = Color.White
                        )
                    }
                }
            }

            MessageType.READY_APPOINTMENT,
            MessageType.VOTE_APPOINTMENT -> {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush = gradientBrush,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .clickable {
                            onCreateAppointChatClick()
                        }
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = message.message,
                            color = Color.White,
                            style = Typography.medium15
                        )
                    }
                }
            }

            MessageType.CONFIRM_APPOINTMENT,
            MessageType.ALL_READY_APPOINTMENT -> {
                Box(
                    modifier = Modifier
                        .background(
                            brush = gradientBrush,
                            shape = RoundedCornerShape(22.dp)
                        )
                        .then(
                            if (message.messageTypeEnum != MessageType.CONFIRM_APPOINTMENT) {
                                Modifier.clickable {
                                    onCreateAppointChatClick()
                                }
                            } else {
                                Modifier
                            }
                        )
                        .padding(vertical = 5.dp, horizontal = 15.dp)
                ) {
                    Text(
                        text = message.message,
                        color = Color.White,
                        style = Typography.medium15
                    )
                }
            }

            null -> {}
        }
    }
}

@Composable
fun ChatTopBar(
    chat: ChatUiState,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit,
    color: Color,
    appointmentStatus: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(Gray)
            .padding(start = 16.dp, end = 16.dp, bottom = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.back_arrow),
                    contentDescription = "뒤로가기",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onBackClick()
                    }
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bulb),
                            contentDescription = null,
                            tint = Gray3,
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            text = formatDateExceptYear(chat.chatInfo?.startDate) + stringResource(
                                id = R.string.lamp_on
                            ),
                            color = Gray3,
                            style = Typography.normal12
                        )
                        Spacer(modifier = Modifier.width(7.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.mypage),
                            contentDescription = null,
                            tint = Gray3,
                            modifier = Modifier.height(10.dp)
                        )
                        // todo ? 뭔가 이상함 숫자가 추후 램프 정상 매칭 가능할때 살펴보기
                        Text(
                            text = "${((chat.chatInfo?.inviteUserCount ?: 0) + 1) / 2} : ${((chat.chatInfo?.inviteUserCount ?: 0) + 1) / 2}",
                            color = Gray3,
                            style = Typography.normal12
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chat.chatInfo?.myLampName ?: "",
                            color = Color.White,
                            style = Typography.medium15
                        )
                        Icon(
                            modifier = Modifier.size(10.dp),
                            painter = painterResource(id = R.drawable.heart),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = chat.chatInfo?.otherLampName ?: "",
                            color = Color.White,
                            style = Typography.medium15
                        )
                    }
                }
            }

            if (appointmentStatus != AppointmentStatus.CONFIRM_APPOINTMENT) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "calendar",
                    tint = color,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onCalendarClick()
                        }
                )
            }
        }
        if (appointmentStatus != AppointmentStatus.EMPTY_APPOINTMENT) {
            HorizontalDivider(thickness = 1.dp, color = LightGray)
        }
        when (appointmentStatus) {
            AppointmentStatus.CONFIRM_APPOINTMENT -> {
                val appointmentTime = chat.appointment!!.selectedChatAppointment!!.meetingTime
                val dateTime =
                    DateFormatUtil.formatIsoToKoreanDate(appointmentTime).split(" ", limit = 2)[1]
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = DateFormatUtil.formatToDDay(appointmentTime),
                        color = color,
                        style = Typography.medium15
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "$dateTime ${chat.appointment.selectedChatAppointment!!.location}",
                        color = color,
                        style = Typography.medium15
                    )
                }
            }

            AppointmentStatus.BEFORE_READY,
            AppointmentStatus.WAITING_READY -> {
                AppointmentStatusSection(
                    text = stringResource(R.string.topbar_before_vote_msg),
                    color = color,
                    onClick = {
                        onCalendarClick()
                    }
                )
            }

            AppointmentStatus.BEFORE_VOTE,
            AppointmentStatus.WAITING_VOTE -> {
                AppointmentStatusSection(
                    text = stringResource(R.string.topbar_vote_msg),
                    color = color,
                    onClick = {
                        onCalendarClick()
                    }
                )
            }
        }
    }
}

@Composable
fun AppointmentStatusSection(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.calendar),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = color
            )
            Text(
                text = text,
                color = color,
                style = Typography.medium15
            )
        }
        Icon(
            painter = painterResource(R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(10.dp),
            tint = color
        )
    }
}

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return ""
    }

    // ISO 8601 형식에 맞는 DateTimeFormatter
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    return try {
        // ZonedDateTime으로 파싱
        val zonedDateTime = ZonedDateTime.parse(dateString, formatter)

        // 출력 형식 정의 (yyyy년 MM월 dd일)
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN)

        // 포맷팅 후 반환
        zonedDateTime.format(outputFormatter)
    } catch (e: Exception) {
        // 파싱 오류가 있을 경우 빈 문자열 반환
        ""
    }
}

fun formatDateExceptYear(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return ""
    }

    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString, formatter)
        val month = zonedDateTime.monthValue
        val day = zonedDateTime.dayOfMonth

        "${month}월 ${day}일"
    } catch (e: Exception) {
        ""
    }
}

fun formatChatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return ""
    }

    // Define the input format with the timezone offset
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")

    // Define the output format
    val outputFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.getDefault())

    return try {
        // Parse the input string as OffsetDateTime
        val date = OffsetDateTime.parse(dateString, inputFormatter)

        // Format the date to the required output format
        date.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}
