package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.lampmatach.LampMatchDataSource
import com.devndev.lamp.domain.repository.LampMatchRepository
import javax.inject.Inject

class LampMatchRepositoryImpl @Inject constructor(
    private val lampMatchDataSource: LampMatchDataSource
) : LampMatchRepository {
    override suspend fun startMatch() {
        lampMatchDataSource.startMatch()
    }

    override suspend fun stopMatch() {
        lampMatchDataSource.stopMatch()
    }
}
