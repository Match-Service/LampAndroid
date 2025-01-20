package com.devndev.lamp.data.datsource.user

import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import com.devndev.lamp.data.service.UserService
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun searchUser(name: String): List<UserResponseDto> {
        return userService.searchUser(name)
    }

    override suspend fun modifyUser(modifyUserRequest: ModifyUserRequest): Response<Void> {
        return userService.modifyUser(modifyUserRequest)
    }

    override suspend fun getMyInfo(): MyInfoResponse {
        return userService.getMyInfo()
    }
}
