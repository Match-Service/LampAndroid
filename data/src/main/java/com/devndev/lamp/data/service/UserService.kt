package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.request.user.PushTokenRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {
    @GET("api/v1/user/invite")
    suspend fun searchInviteUser(
        @Query("userName") name: String
    ): List<UserResponseDto>

    @GET("api/v1/user/visit")
    suspend fun searchVisitUser(
        @Query("userName") name: String
    ): List<UserResponseDto>

    @PATCH("/api/v1/user")
    suspend fun modifyUser(
        @Body modifyUserRequest: ModifyUserRequest
    ): Response<Void>

    @GET("api/v1/user/me")
    suspend fun getMyInfo(): MyInfoResponse

    @PUT("api/v1/user/push-token")
    suspend fun putPushToken(
        @Body pushTokenRequest: PushTokenRequest
    ): Response<Void>
}
