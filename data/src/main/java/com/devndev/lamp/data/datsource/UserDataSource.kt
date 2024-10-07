package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.response.UserResponseDto

interface UserDataSource {
    suspend fun searchUser(name: String): List<UserResponseDto>
}
