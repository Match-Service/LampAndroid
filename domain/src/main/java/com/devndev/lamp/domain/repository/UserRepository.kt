package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.model.user.UserDomainModel

interface UserRepository {
    suspend fun searchUser(name: String): List<UserDomainModel>
//    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Response<Void>

    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Boolean
    suspend fun getMyInfo(): MyInfoDomainModel
}
