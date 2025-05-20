package com.devndev.lamp.data.socket

import android.util.Log
import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.domain.model.chat.ChatMessageDomainModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import javax.inject.Inject

class LampSocketServiceImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LampSocketService {

    private var socket: Socket? = null

    override fun connect(
        onConnected: () -> Unit
    ) {
        val token = localDataSource.getToken()
        Log.d(TAG, "Attempting to connect with token: $token")
        val options = IO.Options().apply {
            extraHeaders = mapOf("authorization" to listOf("Bearer $token"))
        }

        try {
            socket = IO.socket("http://dev-api.lamp-app.xyz:4450/lamp", options)

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d(TAG, "Successfully connected to the socket.")
                onConnected()
            }

            socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.e(TAG, "Socket connection error: ${args.joinToString()}")
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Error during socket connection", e)
        }
    }

    override fun addStatusListener(onMessage: (String) -> Unit, onUpdatedMessage: () -> Unit) {
        removeStatusListener()
        socket?.on("status") { args ->
            Log.d(TAG, "socket on status")
            if (args.isNotEmpty()) {
                try {
                    val jsonObject = JSONObject(args[0].toString())
                    when {
                        jsonObject.has("userLampStatus") -> {
                            val status = jsonObject.getString("userLampStatus")
                            Log.d(TAG, "Received userLampStatus: $status")
                            onMessage(status)
                        }

                        jsonObject.has("updated") -> {
                            onUpdatedMessage()
                            Log.d(TAG, "Received updated")
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing JSON: ${e.message}")
                }
            }
        }
    }

    override fun addChatListener(onChat: (ChatMessageDomainModel) -> Unit) {
        removeChatListener()
        socket?.on("message") { args ->
            Log.d(TAG, "socket on message")
            if (args.isNotEmpty()) {
                try {
                    val jsonObject = JSONObject(args[0].toString())
                    val chatRoomId = jsonObject.getInt("chatRoomId")
                    val message = jsonObject.getString("message")
                    val userId = jsonObject.getInt("userId")
                    val createdAt = jsonObject.getString("createdAt")
                    val id = jsonObject.getString("messageId")
                    Log.d(TAG, "Received message from $userId in $chatRoomId: $message at $createdAt")
                    val chatMessage = ChatMessageDomainModel(
                        id = id,
                        message = message,
                        userId = userId,
                        createdAt = createdAt,
                        chatRoomId = chatRoomId
                    )
                    onChat(chatMessage)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing JSON: ${e.message}")
                }
            }
        }
    }

    override fun removeStatusListener() {
        socket?.off("status")
    }

    override fun removeChatListener() {
        socket?.off("message")
    }

    override fun disconnect() {
        try {
            socket?.disconnect()
            Log.d(TAG, "Socket disconnected")
        } catch (e: Exception) {
            Log.e(TAG, "Error during socket disconnection", e)
        }
    }

    companion object {
        const val TAG = "LampSocketServiceImpl"
    }
}
