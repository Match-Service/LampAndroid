package com.devndev.lamp.presentation.ui.chatting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.devndev.lamp.domain.model.chat.ChatInfoDomainModel
import com.devndev.lamp.domain.model.chat.ChatItem
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import com.devndev.lamp.domain.model.chat.ChatRoomDomainModel
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.chat.GetChatInfoUseCase
import com.devndev.lamp.domain.usecase.chat.GetChatListUseCase
import com.devndev.lamp.domain.usecase.chat.GetChatMessageUseCase
import com.devndev.lamp.domain.usecase.chat.SendChatUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.presentation.ui.home.main.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getChatListUseCase: GetChatListUseCase,
    private val getChatMessageUseCase: GetChatMessageUseCase,
    private val getChatInfoUseCase: GetChatInfoUseCase,
    private val sendChatUseCase: SendChatUseCase
) : ViewModel() {
    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _chatList = MutableStateFlow<List<ChatRoomDomainModel>>(emptyList())
    val chatList: StateFlow<List<ChatRoomDomainModel>> = _chatList

    private val _chatMessage = MutableStateFlow<List<ChatMessageDomainModel>>(emptyList())

    private val _chatInfo = MutableStateFlow<ChatInfoDomainModel?>(null)

    private val _chatUiState = MutableStateFlow(ChatUiState())
    val chatUiState: StateFlow<ChatUiState> = _chatUiState

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getChatList()
        getMyInfo()
    }

    private fun getChatList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d(TAG, "getChatList")
                _chatList.value = getChatListUseCase()
            } catch (e: Exception) {
                Log.e(TAG, "getChatList Exception", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchChatData(
        lastMessageId: String?,
        chatRoomId: Int
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val chatMessageDeferred = async { getChatMessageUseCase(lastMessageId, chatRoomId) }
                val chatInfoDeferred = async { getChatInfoUseCase(chatRoomId) }

                val chatMessage = chatMessageDeferred.await()
                val chatInfo = chatInfoDeferred.await()

                _chatMessage.value = chatMessage
                _chatInfo.value = chatInfo

                updateChatItems()
            } catch (e: Exception) {
                Log.e(TAG, "fetchChatData Exception", e)
                Log.e(TAG, "fetchChatData Exception ${e.cause}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updateChatItems() {
        val messages = _chatMessage.value
        val users = _chatInfo.value?.userInfos ?: return

        val userMap = users.associateBy { it.userId }

        val merged = messages.mapNotNull { message ->
            userMap[message.userId]?.let { userInfo ->
                ChatItem(message, userInfo)
            }
        }

        _chatUiState.value = ChatUiState(
            chatInfo = _chatInfo.value,
            chatMessage = _chatMessage.value,
            chatItems = merged
        )
    }

    fun sendChat(
        chatRoomId: Int,
        message: String
    ) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "sendChat")
                sendChatUseCase(chatRoomId, message)
            } catch (e: Exception) {
                Log.e(TAG, "getChatInfo Exception", e)
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            try {
                _myInfo.value = getMyInfoUseCase()
                Log.d(HomeViewModel.TAG, "My Info ${myInfo.value}")
            } catch (e: HttpException) {
                Log.e(HomeViewModel.TAG, "fetchData HttpException", e)
            } catch (e: Exception) {
                Log.e(HomeViewModel.TAG, "fetchData Exception", e)
            }
        }
    }

    companion object {
        const val TAG = "ChatViewModel"
    }
}
