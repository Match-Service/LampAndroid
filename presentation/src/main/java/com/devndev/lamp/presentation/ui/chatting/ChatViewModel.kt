package com.devndev.lamp.presentation.ui.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.chat.ChatItem
import com.devndev.lamp.domain.usecase.chat.GetChatInfoUseCase
import com.devndev.lamp.domain.usecase.chat.GetChatListUseCase
import com.devndev.lamp.domain.usecase.chat.GetChatMessageUseCase
import com.devndev.lamp.domain.usecase.chat.SendChatUseCase
import com.devndev.lamp.domain.usecase.socket.AddChatListenerUseCase
import com.devndev.lamp.domain.usecase.socket.RemoveChatListenerUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val getChatMessageUseCase: GetChatMessageUseCase,
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val sendChatUseCase: SendChatUseCase,
    private val addChatListenerUseCase: AddChatListenerUseCase,
    private val removeChatListenerUseCase: RemoveChatListenerUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        getChatList()
        getMyInfo()
    }

    fun getChatList() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getChatListUseCase()
                .onSuccess { chatList ->
                    Log.d(TAG, "getChatList Success")
                    val sortedList = chatList.sortedBy { chatRoom ->
                        val dateString = chatRoom.lastMessageInfo?.createdAt ?: chatRoom.startDate
                        parseDate(dateString)
                    }
                    _uiState.update { it.copy(chatList = sortedList) }
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    Log.e(TAG, "getChatList Failure", e)
                    _uiState.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun updateChatList() {
        viewModelScope.launch {
            getChatListUseCase()
                .onSuccess { chatList ->
                    Log.d(TAG, "getChatList Success")
                    val sortedList = chatList.sortedBy { chatRoom ->
                        val dateString = chatRoom.lastMessageInfo?.createdAt ?: chatRoom.startDate
                        parseDate(dateString)
                    }
                    _uiState.update { it.copy(chatList = sortedList) }
                }
                .onFailure { e ->
                    Log.e(TAG, "getChatList Failure", e)
                }
        }
    }

    fun fetchChatData(
        lastMessageId: String?,
        chatRoomId: Int,
        onPrependComplete: ((newItemCount: Int) -> Unit)? = null
    ) {
        // 중복 요청 방지
        if (lastMessageId != null && lastMessageId == uiState.value.lastFetchedMessageId) {
            return
        }
        Log.d(TAG, "fetchChatData lastMessageId $lastMessageId")
        _uiState.update { it.copy(isLoading = true, lastFetchedMessageId = lastMessageId) }
        viewModelScope.launch {
            val chatMessageResult = getChatMessageUseCase(lastMessageId, chatRoomId)
            val chatInfoResult = getChatInfoUseCase(chatRoomId)

            val previousSize = uiState.value.chatMessage.size

            chatMessageResult
                .onSuccess { newMessage ->
                    Log.d(TAG, newMessage.toString())
                    val message = if (lastMessageId == null) {
                        newMessage
                    } else {
                        newMessage + uiState.value.chatMessage
                    }
                    _uiState.update { it.copy(chatMessage = message) }
                }
                .onFailure {
                    Log.e(TAG, "getChatMessage Failure", it)
                }

            chatInfoResult
                .onSuccess { chatInfo ->
                    _uiState.update { it.copy(chatInfo = chatInfo) }
                }
                .onFailure {
                    Log.e(TAG, "getChatInfo Failure", it)
                }

            if (chatMessageResult.isSuccess && chatInfoResult.isSuccess) {
                updateChatItems()
            }
            onPrependComplete?.invoke(uiState.value.chatMessage.size - previousSize)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun updateChatItems() {
        val messages = uiState.value.chatMessage
        val users = uiState.value.chatInfo?.userInfos ?: return

        val userMap = users.associateBy { it.userId }

        val merged = messages.mapNotNull { message ->
            userMap[message.userId]?.let { userInfo ->
                ChatItem(message, userInfo)
            }
        }

        _uiState.update { it.copy(chatItems = merged) }
    }

    fun sendChat(
        chatRoomId: Int,
        message: String
    ) {
        viewModelScope.launch {
            sendChatUseCase(chatRoomId, message)
                .onSuccess {
                    Log.d(TAG, "sendChat Success")
                }
                .onFailure {
                    Log.e(TAG, "sendChat Failure", it)
                }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { userInfo ->
                    Log.d(TAG, "getMyInfo")
                    _uiState.update { it.copy(myInfo = userInfo) }
                    Log.d(TAG, "My Info ${uiState.value.myInfo}")
                }
                .onFailure { throwable ->
                    Log.e(TAG, "Failed to fetch user info", throwable)
                }
        }
    }

    fun addChatListener(chatRoomId: Int) {
        viewModelScope.launch {
            try {
                addChatListenerUseCase(
                    onChat = { chatMessage ->
                        Log.d(TAG, "onChat")
                        if (chatMessage.chatRoomId == chatRoomId) {
                            val updatedMessages = uiState.value.chatMessage + chatMessage
                            _uiState.update { it.copy(chatMessage = updatedMessages) }

                            updateChatItems()
                            if (chatMessage.userId == uiState.value.myInfo?.userId) {
                                setNeedScrollDown(true)
                            }
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "addChatListener failed", e)
            }
        }
    }

    fun addChatListenerForChatList() {
        viewModelScope.launch {
            Log.d(TAG, "addChatListenerForChatList")
            try {
                addChatListenerUseCase(
                    onChat = { _ ->
                        Log.d(TAG, "onChat")
                        updateChatList()
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "addChatListener failed", e)
            }
        }
    }

    fun removeChatListener() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "removeChatListener")
                removeChatListenerUseCase()
            } catch (e: Exception) {
                Log.e(TAG, "removeChatListener error", e)
            }
        }
    }

    fun setNeedScrollDown(needScrollDown: Boolean) {
        _uiState.update { it.copy(needScrollDown = needScrollDown) }
    }

    private fun parseDate(dateStr: String): Date {
        return try {
            val odt = OffsetDateTime.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            Date.from(odt.toInstant())
        } catch (e: Exception) {
            Date(0)
        }
    }

    companion object {
        const val TAG = "ChatViewModel"
    }
}
