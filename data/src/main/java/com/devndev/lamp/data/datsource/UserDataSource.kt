package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.response.MyInfoResponse
import com.devndev.lamp.data.dto.response.UserResponseDto

interface UserDataSource {
    suspend fun searchUser(name: String): List<UserResponseDto>
    suspend fun getMyInfo(): MyInfoResponse
}
