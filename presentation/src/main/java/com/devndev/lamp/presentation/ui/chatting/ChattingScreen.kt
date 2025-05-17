package com.devndev.lamp.presentation.ui.chatting

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.IncTypography
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.ManColor
import com.devndev.lamp.presentation.theme.Typography
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.system.exitProcess

@Composable
fun ChatListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val logTag = "ChatListScreen"
    val chatList by viewModel.chatList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity
    val handler = remember { Handler(Looper.getMainLooper()) }
    var backPressedOnce = remember { false }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getChatList()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler {
        Log.d(logTag, "back button clicked")
        if (backPressedOnce) {
            Log.d(logTag, "back button clicked 2 times end process")
            exitProcess(0)
        } else {
            Log.d(logTag, "back button clicked 1 times show Toast")
            Toast.makeText(
                context,
                context.getString(R.string.back_button_end_message),
                Toast.LENGTH_SHORT
            ).show()
            backPressedOnce = true
            handler.postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(LampBlack))
    } else {
        if (chatList.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                reverseLayout = true
            ) {
                items(chatList) { chat ->
                    Chat(
                        onChatClick = {
                            val intent = Intent(context, ChatActivity::class.java).apply {
                                putExtra("chatRoomId", chat.chatRoomId)
                            }
                            context.startActivity(intent)
                            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.none)
                        },
                        chat = chat
                    )
                }
            }
        } else {
            EmptyChatScreen()
        }
    }
}

@Composable
fun EmptyChatScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1.5f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Text(
                text = stringResource(id = R.string.empty_chatting),
                color = ManColor,
                style = IncTypography.normal42
            )
            Text(
                text = stringResource(id = R.string.guide_empty_chatting),
                color = Color.White,
                style = Typography.normal12
            )
        }
        Spacer(modifier = Modifier.weight(3f))
    }
}

fun formatToMonthDay(dateString: String): String {
    // 'yyyy-MM-dd'T'HH:mm:ssXXX' 형식으로 변경하여 시간대 처리
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

    // 입력 날짜를 파싱
    val date = inputFormat.parse(dateString)

    // 출력 형식은 "M월 d일"
    val outputFormat = SimpleDateFormat("M월 d일", Locale.KOREAN)

    // 한국 시간대(KST)로 변환하기 위해 outputFormat에 TimeZone 설정
    outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

    return date?.let { outputFormat.format(it) } ?: ""
}
