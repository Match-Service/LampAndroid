package com.devndev.lamp.data.repository

import android.util.Log
import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lamp.RejectInviteParam
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
        val response = lampDataSource.inviteUser(lampId, inviteUsersRequest)
        if (response.isSuccessful) {
            Log.d("InviteUser", "User invited successfully, Status Code: ${response.code()}")
        } else {
            Log.e("InviteUser", "Failed to invite user, Status Code: ${response.code()}")
        }
    }

    override suspend fun acceptInvite(lampId: Int, acceptInviteParam: AcceptInviteParam) {
        val acceptUserRequest = AcceptInviteRequest(
            inviteRequestUserId = acceptInviteParam.inviteRequestUserId,
            alarmId = acceptInviteParam.alarmId
        )
        val response = lampDataSource.acceptInvite(lampId, acceptUserRequest)

        if (response.isSuccessful) {
            Log.d("AcceptInvite", "AcceptInvite successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                "AcceptInvite",
                "Failed to AcceptInvite, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun rejectInvite(lampId: Int, rejectInviteParam: RejectInviteParam) {
        val rejectInviteRequest = RejectInviteRequest(
            inviteRequestUserId = rejectInviteParam.inviteRequestUserId,
            alarmId = rejectInviteParam.alarmId
        )
        val response = lampDataSource.rejectInvite(lampId, rejectInviteRequest)

        if (response.isSuccessful) {
            Log.d("RejectInvite", "RejectInvite successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                "RejectInvite",
                "Failed to RejectInvite, Status Code: ${response.code()} ${response.raw()}"
            )
        }
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

    override suspend fun visitRequest(lampId: Int) {
        lampDataSource.requestVisit(lampId)
    }
}
