package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.lamp.VoteAcceptRequest
import com.devndev.lamp.data.dto.request.lamp.VoteRejectRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LampMatchService {
    @POST("api/v1/lamp-match/start")
    suspend fun startMatch()

    @POST("api/v1/lamp-match/stop")
    suspend fun stopMatch()

    @POST("api/v1/lamp-match/accept")
    suspend fun accept(
        @Body voteAcceptRequest: VoteAcceptRequest
    ): Response<Unit>

    @POST("api/v1/lamp-match/reject")
    suspend fun reject(
        @Body voteRejectRequest: VoteRejectRequest
    ): Response<Unit>

    @GET("api/v1/lamp-match")
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
