package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.CreateLampParam

interface LampRepository {
    suspend fun createLamp(createLampParam: CreateLampParam): Int
}
