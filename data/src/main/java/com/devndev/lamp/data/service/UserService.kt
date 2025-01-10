package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.ModifyUserRequest
import com.devndev.lamp.data.dto.response.MyInfoResponse
import com.devndev.lamp.data.dto.response.UserResponseDto
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
//        @Query("name") name: String,
//        @Query("job") job: String,
//        @Query("jobName") jobName: String,
//        @Query("gender") gender: String,
//        @Query("birth") birth: String,
//        @Query("instagramId") instagramId: String,
//        @Query("bio") bio: String,
//        @Query("profileImages") profileImages: List<String>,
//        @Query("alarmSetting") alarmSetting: AlarmSetting,
//        @Query("bioQuestion") bioQuestion: List<BioQuestion>,
//        @Query("pushToken") pushToken: String,
    ): Response<Void>

    @GET("api/v1/user/me")
    suspend fun getMyInfo(): MyInfoResponse
}
