package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.response.MyInfoResponse
import com.devndev.lamp.data.dto.response.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("api/v1/user/find")
    suspend fun searchUser(
        @Query("userName") name: String
    ): List<UserResponseDto>

    @GET("api/v1/user/me")
    suspend fun getMyInfo(): MyInfoResponse
}
