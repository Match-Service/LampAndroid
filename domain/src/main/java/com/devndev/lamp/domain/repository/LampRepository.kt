package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel

interface LampRepository {
    suspend fun createLamp(createLampParam: CreateLampParam): Int
    suspend fun getMyLamp(): LampDomainModel
}
