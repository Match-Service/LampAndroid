package com.devndev.lamp.presentation.ui.chatting

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.UserInfo
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.theme.WomanColor
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.main.LampTopBar
import com.devndev.lamp.presentation.ui.main.navigation.navigateMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import java.text.ParseException
import java.text.SimpleDateFormat
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
    var isLoading by remember { mutableStateOf(true) }

    BackHandler {
        navController.navigateMain(MainScreenPage.CHATTING)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchChatData(lastMessageId = null, chatRoomId = chatRoomId)
        delay(300)
        isLoading = false
    }
    val lazyListState = rememberLazyListState()

    var currentMessage by remember { mutableStateOf("") }

    if (!isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LampTopBar(navController = navController, isAlarmIconNeed = true, Gray)
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

            LaunchedEffect(chat.value.chatItems.size) {
                if (chat.value.chatItems.isNotEmpty()) {
                    snapshotFlow { lazyListState.layoutInfo.totalItemsCount }
                        .filter { it > 0 }
                        .first()

                    lazyListState.scrollToItem(chat.value.chatItems.size)
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
                                viewModel.fetchChatData(null, chatRoomId)
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
                                viewModel.fetchChatData(null, chatRoomId)
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
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Gray)
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

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return ""
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())

    return try {
        val date = inputFormat.parse(dateString)
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: ParseException) {
        ""
    }
}

fun formatChatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return ""
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    val outputFormat = SimpleDateFormat("a hh:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(dateString)
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (e: ParseException) {
        ""
    }
}
