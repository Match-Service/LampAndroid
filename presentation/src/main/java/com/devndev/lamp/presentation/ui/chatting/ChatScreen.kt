package com.devndev.lamp.presentation.ui.chatting

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.UserInfo
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.main.LampTopBar
import com.devndev.lamp.presentation.ui.main.navigation.navigateMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
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
    val chat = viewModel.chatUiState.collectAsState()
    val myInfo = viewModel.myInfo.collectAsState()
    val topBarVisible = remember { mutableStateOf(true) }
    var shouldNavigate by remember { mutableStateOf(false) }
    val isLoading = viewModel.isLoading.collectAsState()
    val needScrollDown = viewModel.needScrollDown.collectAsState()

    var previousItemCount by remember { mutableStateOf(0) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        viewModel.connectSocketForChatRoom(chatRoomId)
        onDispose {
            viewModel.disconnectSocket()
        }
    }

    BackHandler {
        topBarVisible.value = false
        shouldNavigate = true
    }

    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            delay(200) // Wait for the animation to finish
            navController.navigateMain(MainScreenPage.CHATTING) // Navigate to the next screen
        }
    }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(needScrollDown.value) {
        if (needScrollDown.value) {
            lazyListState.scrollToItem(chat.value.chatItems.size)
            viewModel.setNeedScrollDown(false)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchChatData(lastMessageId = null, chatRoomId = chatRoomId)
        delay(300)
        topBarVisible.value = true
        lazyListState.scrollToItem(chat.value.chatItems.size)
    }

    var currentMessage by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .imePadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LampTopBar(navController = navController, isAlarmIconNeed = true, color = Gray)
        AnimatedVisibility(
            visible = topBarVisible.value,
            enter = EnterTransition.None,
            exit = slideOutVertically(animationSpec = tween(200)) + shrinkVertically() + fadeOut()
        ) {
            ChatTopBar(
                chat = chat.value,
                onBackClick = {
                    topBarVisible.value = false
                    shouldNavigate = true
                },
                onCalendarClick = {}
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = lazyListState
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
                            text = formatDate(chat.value.chatInfo?.startDate),
                            color = Color.White,
                            style = Typography.normal12
                        )
                    }
                }
                if (!isLoading.value && chat.value.chatMessage.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(id = R.string.chat_created) +
                                "\n" +
                                stringResource(id = R.string.say_hi),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = Typography.normal12
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
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

            items(chat.value.chatItems) { chat ->
                ChatBubble(
                    message = chat.message,
                    userInfo = chat.userInfo,
                    isMine = (chat.userInfo.userId == myInfo.value?.userId)
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        LaunchedEffect(lazyListState) {
            snapshotFlow { lazyListState.firstVisibleItemIndex }
                .filter { it == 0 } // 최상단에 도달했을 때만
                .collect { index ->
                    val firstVisibleMessage = chat.value.chatItems.getOrNull(index)?.message
                    val firstMessageId = firstVisibleMessage?.id
                    Log.d("----", firstMessageId.toString())
                    Log.d("----", firstVisibleMessage?.message?:"")

                    if (firstMessageId != null) {
                        viewModel.fetchChatData(
                            lastMessageId = firstMessageId,
                            chatRoomId = chatRoomId
                        )
                    }
                }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(color = Gray)
                .padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = 20.dp)
        ) {
            BasicTextField(
                modifier = Modifier
                    .background(Gray)
                    .weight(1f)
                    .clip(RoundedCornerShape(27.dp))
                    .border(1.dp, LightGray, RoundedCornerShape(27.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                value = currentMessage,
                onValueChange = { currentMessage = it },
                textStyle = Typography.medium15.copy(color = Gray3),
                singleLine = true,
                cursorBrush = SolidColor(Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (currentMessage.isNotBlank()) {
                            viewModel.sendChat(chatRoomId, currentMessage)
                            currentMessage = ""
                        }
                    }
                )
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
}

@Composable
fun ChatBubble(
    message: ChatMessageDomainModel,
    userInfo: UserInfo,
    isMine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
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
}

@Composable
fun ChatTopBar(
    chat: ChatUiState,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
            .background(Gray)
            .padding(start = 16.dp, end = 16.dp, bottom = 15.dp),
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
                        text = formatDateExceptYear(chat.chatInfo?.startDate) + stringResource(id = R.string.lamp_on),
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
        Icon(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = "calendar",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
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
