package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.domain.repository.ConfigRepository
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : ConfigRepository {
    override fun saveIsFirstOpen(isFirstOpen: Boolean) {
        localDataSource.saveIsFirstOpen(isFirstOpen)
    }

    override fun getIsFirstOpen(): Boolean {
        return localDataSource.getIsFirstOpen()
    }
}
