package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.ModifyUserParam
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.model.UserDomainModel

interface UserRepository {
    suspend fun searchUser(name: String): List<UserDomainModel>
//    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Response<Void>

    suspend fun modifyUser(modifyUserParam: ModifyUserParam): Boolean
    suspend fun getMyInfo(): MyInfoDomainModel
}
