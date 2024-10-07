package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.response.UserResponseDto
import com.devndev.lamp.data.service.UserService
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun searchUser(name: String): List<UserResponseDto> {
        return userService.searchUser(name)
    }
}
