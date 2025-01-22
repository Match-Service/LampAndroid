package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel

interface LampRepository {
    suspend fun createLamp(createLampParam: CreateLampParam): Int
    suspend fun getMyLamp(): LampDomainModel
    suspend fun deleteLamp(lampId: Int)
    suspend fun inviteUser(lampId: Int, inviteUsersParam: InviteUsersParam)
    suspend fun exitLamp(lampId: Int)
    suspend fun kickUser(lampId: Int, kickUserParam: KickUserParam)
}
