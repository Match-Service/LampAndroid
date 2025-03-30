package com.devndev.lamp.data.socket

import android.util.Log
import com.devndev.lamp.data.datsource.local.LocalDataSource
import io.socket.client.IO
import io.socket.client.Socket
import javax.inject.Inject

class LampSocketServiceImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LampSocketService {

    private var socket: Socket? = null
    private val logTag = "LampSocketService"

    override fun connect(onConnected: () -> Unit, onMessage: (String) -> Unit) {
        val token = localDataSource.getToken()
        Log.d(logTag, "Attempting to connect with token: $token")
        val options = IO.Options().apply {
            extraHeaders = mapOf("authorization" to listOf("Bearer $token"))
        }

        try {
            socket = IO.socket("http://dev-api.lamp-app.xyz:4450", options)

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d(logTag, "Successfully connected to the socket.")
                onConnected()
            }

            socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.e(logTag, "Socket connection error: ${args.joinToString()}")
            }

            socket?.on("status") { args ->
                if (args.isNotEmpty()) {
                    val status = args[0] as String
                    Log.d(logTag, "Received status: $status")
                    onMessage(status)
                }
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e(logTag, "Error during socket connection", e)
        }
    }

    override fun disconnect() {
        try {
            socket?.disconnect()
            Log.d(logTag, "Socket disconnected")
        } catch (e: Exception) {
            Log.e(logTag, "Error during socket disconnection", e)
        }
    }
}
