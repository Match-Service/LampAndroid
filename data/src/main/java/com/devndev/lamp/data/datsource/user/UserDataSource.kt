package com.devndev.lamp.data.datsource.user

import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import retrofit2.Response

interface UserDataSource {
    suspend fun searchUser(name: String): List<UserResponseDto>
    suspend fun modifyUser(modifyUserRequest: ModifyUserRequest): Response<Void>
    suspend fun getMyInfo(): MyInfoResponse
}
