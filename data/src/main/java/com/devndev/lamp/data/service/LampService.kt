package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.AcceptVisitRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.data.dto.request.lamp.RejectVisitRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import com.devndev.lamp.data.dto.response.lamp.VisitRequestLampInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LampService {
    @POST("api/v1/lamp")
    suspend fun createLamp(
        @Body createLampRequest: CreateLampRequest
    ): CreateLampResponse

    @GET("api/v1/lamp")
    suspend fun getMyLamp(): LampResponse

    @DELETE("api/v1/lamp")
    suspend fun deleteLamp(): Response<Unit>

    @PUT("api/v1/lamp")
    suspend fun editLamp(
        @Body createLampRequest: CreateLampRequest
    )

    @POST("api/v1/lamp/invite/request")
    suspend fun inviteUsers(
        @Body inviteUserRequest: InviteUsersRequest
    ): Response<Unit>

    @POST("api/v1/lamp/invite/accept")
    suspend fun acceptInvite(
        @Body acceptInviteRequest: AcceptInviteRequest
    ): Response<Unit>

    @POST("api/v1/lamp/invite/reject")
    suspend fun rejectInvite(
        @Body rejectInviteRequest: RejectInviteRequest
    ): Response<Unit>

    @POST("api/v1/lamp/out")
    suspend fun exitLamp(): Response<Unit>

    @POST("api/v1/lamp/kick")
    suspend fun kickUser(
        @Body kickUserRequest: KickUserRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/visit/request")
    suspend fun requestVisit(
        @Path("lampId") lampId: Int
    ): Response<Unit>

    @POST("api/v1/lamp/visit/accept")
    suspend fun acceptVisit(
        @Body acceptVisitRequest: AcceptVisitRequest
    ): Response<Unit>

    @POST("api/v1/lamp/visit/reject")
    suspend fun rejectVisit(
        @Body rejectVisitRequest: RejectVisitRequest
    ): Response<Unit>

    @GET("api/v1/lamp/visit/request")
    suspend fun getVisitRequestLampInfo(): VisitRequestLampInfoResponse

    @DELETE("api/v1/lamp/visit/request")
    suspend fun cancelVisitRequest(): Response<Unit>
}
