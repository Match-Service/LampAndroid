package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {
    @GET("api/v1/user/find")
    suspend fun searchUser(
        @Query("userName") name: String
    ): List<UserResponseDto>

    @PATCH("/api/v1/user")
    suspend fun modifyUser(
        @Body modifyUserRequest: ModifyUserRequest
    ): Response<Void>

    @GET("api/v1/user/me")
    suspend fun getMyInfo(): MyInfoResponse
}
