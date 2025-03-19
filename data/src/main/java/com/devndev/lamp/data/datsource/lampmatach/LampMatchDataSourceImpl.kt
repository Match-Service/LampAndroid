package com.devndev.lamp.data.datsource.lampmatach

import com.devndev.lamp.data.service.LampMatchService
import javax.inject.Inject

class LampMatchDataSourceImpl @Inject constructor(
    private val lampMatchService: LampMatchService
) : LampMatchDataSource {
    override suspend fun startMatch() {
        lampMatchService.startMatch()
    }

    override suspend fun stopMatch() {
        lampMatchService.stopMatch()
    }
}
