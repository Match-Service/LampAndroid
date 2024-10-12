package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.UserDomainModel

interface UserRepository {
    suspend fun searchUser(name: String): List<UserDomainModel>
}
