package com.devndev.lamp.data.datsource.user

import com.devndev.lamp.data.dto.request.user.EditImageRequest
import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.request.user.PushTokenRequest
import com.devndev.lamp.data.dto.response.user.MyInfoResponse
import com.devndev.lamp.data.dto.response.user.UserResponseDto
import com.devndev.lamp.data.dto.response.user.UserStatusResponse
import com.devndev.lamp.data.service.UserService
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService
) : UserDataSource {
    override suspend fun searchInviteUser(name: String): List<UserResponseDto> {
        return userService.searchInviteUser(name)
    }

    override suspend fun searchVisitUser(name: String): List<UserResponseDto> {
        return userService.searchVisitUser(name)
    }

    override suspend fun modifyUser(modifyUserRequest: ModifyUserRequest): Response<Void> {
        return userService.modifyUser(modifyUserRequest)
    }

//    override suspend fun editImage(files: List<MultipartBody.Part>): List<ProfileImageResponse> {
//        return userService.editImages(files)
//    }
    override suspend fun editImage(editImageRequest: EditImageRequest): Response<Void> {
        return userService.editImages(editImageRequest)
    }

    override suspend fun getMyInfo(): MyInfoResponse {
        return userService.getMyInfo()
    }

    override suspend fun putPushToken(pushTokenRequest: PushTokenRequest): Response<Void> {
        return userService.putPushToken(pushTokenRequest)
    }

    override suspend fun getUserStatus(): UserStatusResponse {
        return userService.getUserStatus()
    }
}
