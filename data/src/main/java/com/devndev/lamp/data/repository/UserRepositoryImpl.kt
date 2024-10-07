package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.UserDataSource
import com.devndev.lamp.data.dto.response.toDomainModel
import com.devndev.lamp.domain.model.UserDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun searchUser(name: String): List<UserDomainModel> {
        val userResponseDtos = userDataSource.searchUser(name)
        return userResponseDtos.toDomainModel()
    }
}
