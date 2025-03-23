package com.devndev.lamp.data.datsource.user

import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.request.user.PushTokenRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import com.devndev.lamp.data.dto.response.user.UserStatusResponse
import retrofit2.Response

interface UserDataSource {
    suspend fun searchInviteUser(name: String): List<UserResponseDto>
    suspend fun searchVisitUser(name: String): List<UserResponseDto>
    suspend fun modifyUser(modifyUserRequest: ModifyUserRequest): Response<Void>
    suspend fun getMyInfo(): MyInfoResponse
    suspend fun putPushToken(pushTokenRequest: PushTokenRequest): Response<Void>
    suspend fun getUserStatus(): UserStatusResponse
}
