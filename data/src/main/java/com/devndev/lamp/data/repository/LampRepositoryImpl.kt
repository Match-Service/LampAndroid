package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.repository.LampRepository
import javax.inject.Inject

class LampRepositoryImpl @Inject constructor(
    private val lampDataSource: LampDataSource
) : LampRepository {
    override suspend fun createLamp(createLampParam: CreateLampParam): Int {
        val makeLampRequest = CreateLampRequest(
            name = createLampParam.name,
            description = createLampParam.description,
            hopeMatchNumber = createLampParam.hopeMatchNumber,
            location = createLampParam.location,
            color = createLampParam.color
        )
        return lampDataSource.createLamp(makeLampRequest).lampId
    }

    override suspend fun getMyLamp(): LampDomainModel {
        return lampDataSource.getMyInfo().toDomainModel()
    }
}
