package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.lamp.AcceptInviteRequest
import com.devndev.lamp.data.dto.request.lamp.AcceptVisitRequest
import com.devndev.lamp.data.dto.request.lamp.CreateLampRequest
import com.devndev.lamp.data.dto.request.lamp.InviteUsersRequest
import com.devndev.lamp.data.dto.request.lamp.KickUserRequest
import com.devndev.lamp.data.dto.request.lamp.RejectInviteRequest
import com.devndev.lamp.data.dto.response.lamp.CreateLampResponse
import com.devndev.lamp.data.dto.response.lamp.LampResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LampService {
    @POST("api/v1/lamp")
    suspend fun createLamp(
        @Body createLampRequest: CreateLampRequest
    ): CreateLampResponse

    @GET("api/v1/lamp")
    suspend fun getMyLamp(): LampResponse

    @DELETE("api/v1/lamp/{lampId}")
    suspend fun deleteLamp(
        @Path("lampId") lampId: Int
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/invite/request")
    suspend fun inviteUsers(
        @Path("lampId") lampId: Int,
        @Body inviteUserRequest: InviteUsersRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/invite/accept")
    suspend fun acceptInvite(
        @Path("lampId") lampId: Int,
        @Body acceptInviteRequest: AcceptInviteRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/invite/reject")
    suspend fun rejectInvite(
        @Path("lampId") lampId: Int,
        @Body rejectInviteRequest: RejectInviteRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/out")
    suspend fun exitLamp(
        @Path("lampId") lampId: Int
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/kick")
    suspend fun kickUser(
        @Path("lampId") lampId: Int,
        @Body kickUserRequest: KickUserRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/visit/request")
    suspend fun requestVisit(
        @Path("lampId") lampId: Int
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/visit/accept")
    suspend fun acceptVisit(
        @Path("lampId") lampId: Int,
        @Body acceptVisitRequest: AcceptVisitRequest
    ): Response<Unit>

    @POST("api/v1/lamp/{lampId}/visit/reject")
    suspend fun rejectVisit(
        @Path("lampId") lampId: Int,
        @Body acceptVisitRequest: AcceptVisitRequest
    ): Response<Unit>
}
