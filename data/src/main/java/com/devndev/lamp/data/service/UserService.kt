package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.response.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("api/v1/users")
    suspend fun searchUser(
        @Query("name") name: String
    ): List<UserResponseDto>
}
