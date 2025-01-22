package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.KickUserParam
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

    override suspend fun deleteLamp(lampId: Int) {
        lampDataSource.deleteLamp(lampId)
    }

    override suspend fun inviteUser(lampId: Int, inviteUsersParam: InviteUsersParam) {
        val inviteUsersRequest = InviteUsersRequest(
            inviteUserIds = inviteUsersParam.inviteUserIds
        )
        lampDataSource.inviteUser(lampId, inviteUsersRequest)
    }

    override suspend fun exitLamp(lampId: Int) {
        lampDataSource.exitLamp(lampId)
    }

    override suspend fun kickUser(lampId: Int, kickUserParam: KickUserParam) {
        val kickUserRequest = KickUserRequest(
            kickUserId = kickUserParam.kickUserId
        )
        lampDataSource.kickUser(lampId, kickUserRequest)
    }
}
