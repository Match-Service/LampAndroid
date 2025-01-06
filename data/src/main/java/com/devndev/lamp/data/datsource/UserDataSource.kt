package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.ModifyUserRequest
import com.devndev.lamp.data.dto.response.MyInfoResponse
import com.devndev.lamp.data.dto.response.UserResponseDto
import retrofit2.Response

interface UserDataSource {
    suspend fun searchUser(name: String): List<UserResponseDto>
    suspend fun modifyUser(modifyUserRequest: ModifyUserRequest): Response<Void>
    suspend fun getMyInfo(): MyInfoResponse
}
