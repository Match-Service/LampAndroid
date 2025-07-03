package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : LocalRepository {
    override suspend fun putBoolean(key: String, value: Boolean) {
        localDataSource.putBoolean(key, value)
    }

    override suspend fun getBoolean(key: String, value: Boolean): Boolean {
        return localDataSource.getBoolean(key, value)
    }
}
