package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.user.EditImageParam
import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.model.user.PushTokenParam
import com.devndev.lamp.domain.model.user.UserDomainModel

interface UserRepository {
//    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Response<Void>
    suspend fun searchInviteUser(name: String): List<UserDomainModel>
    suspend fun searchVisitUser(name: String): List<UserDomainModel>
    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Boolean
    suspend fun editImage(files: EditImageParam): Boolean
    suspend fun getMyInfo(): MyInfoDomainModel
    suspend fun putPushToken(pushTokenParam: PushTokenParam)
}
