package com.devndev.lamp.data.repository

import android.util.Log
import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.AcceptVisitRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.data.dto.request.lamp.RejectVisitRequest
import com.devndev.lamp.domain.model.lamp.AcceptInviteParam
import com.devndev.lamp.domain.model.lamp.AcceptVisitParam
import com.devndev.lamp.domain.model.lamp.CreateLampParam
import com.devndev.lamp.domain.model.lamp.InviteUsersParam
import com.devndev.lamp.domain.model.lamp.KickUserParam
import com.devndev.lamp.domain.model.lamp.LampDomainModel
import com.devndev.lamp.domain.model.lamp.RejectInviteParam
import com.devndev.lamp.domain.model.lamp.RejectVisitParam
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

    override suspend fun deleteLamp() {
        lampDataSource.deleteLamp()
    }

    override suspend fun editLamp(createLampParam: CreateLampParam) {
        val editLampRequest = CreateLampRequest(
            name = createLampParam.name,
            description = createLampParam.description,
            hopeMatchNumber = createLampParam.hopeMatchNumber,
            location = createLampParam.location,
            color = createLampParam.color
        )
        lampDataSource.editLamp(editLampRequest)
    }

    override suspend fun inviteUser(inviteUsersParam: InviteUsersParam) {
        val inviteUsersRequest = InviteUsersRequest(
            inviteUserIds = inviteUsersParam.inviteUserIds
        )
        val response = lampDataSource.inviteUser(inviteUsersRequest)
        if (response.isSuccessful) {
            Log.d(TAG, "User invited successfully, Status Code: ${response.code()}")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            Log.e(TAG, "Failed to invite user, Status Code: ${response.code()} Error $errorBody")
        }
    }

    override suspend fun acceptInvite(acceptInviteParam: AcceptInviteParam) {
        val acceptUserRequest = AcceptInviteRequest(
            inviteRequestUserId = acceptInviteParam.inviteRequestUserId,
            alarmId = acceptInviteParam.alarmId
        )
        val response = lampDataSource.acceptInvite(acceptUserRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "AcceptInvite successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to AcceptInvite, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun rejectInvite(rejectInviteParam: RejectInviteParam) {
        val rejectInviteRequest = RejectInviteRequest(
            inviteRequestUserId = rejectInviteParam.inviteRequestUserId,
            alarmId = rejectInviteParam.alarmId
        )
        val response = lampDataSource.rejectInvite(rejectInviteRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "RejectInvite successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to RejectInvite, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun exitLamp() {
        lampDataSource.exitLamp()
    }

    override suspend fun kickUser(kickUserParam: KickUserParam) {
        val kickUserRequest = KickUserRequest(
            kickUserId = kickUserParam.kickUserId
        )
        lampDataSource.kickUser(kickUserRequest)
    }

    override suspend fun visitRequest(lampId: Int) {
        lampDataSource.requestVisit(lampId)
    }

    override suspend fun acceptVisit(acceptVisitParam: AcceptVisitParam) {
        val acceptVisitRequest = AcceptVisitRequest(
            visitUserId = acceptVisitParam.visitUserId,
            alarmId = acceptVisitParam.alarmId
        )
        val response = lampDataSource.acceptVisit(acceptVisitRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "AcceptVisit successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to AcceptVisit, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun rejectVisit(rejectVisitParam: RejectVisitParam) {
        val rejectVisitRequest = RejectVisitRequest(
            visitUserId = rejectVisitParam.visitUserId,
            alarmId = rejectVisitParam.alarmId
        )
        val response = lampDataSource.rejectVisit(rejectVisitRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "RejectVisit successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to RejectVisit, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun getVisitRequestLampInfo(): String {
        return lampDataSource.getVisitRequestLampInfo().name
    }

    override suspend fun cancelVisitRequest() {
        val response = lampDataSource.cancelVisitRequest()
        if (response.isSuccessful) {
            Log.d(TAG, "CancelVisitRequest Success, Status Code: ${response.code()}")
        } else {
            Log.d(
                TAG,
                "CancelVisitRequest Failure, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    companion object {
        const val TAG = "LampRepositoryImpl"
    }
}
