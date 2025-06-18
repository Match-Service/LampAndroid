package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.lampmatch.AcceptVoteRequest
import com.devndev.lamp.data.dto.request.lampmatch.RejectVoteRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
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
        @Body acceptVoteRequest: AcceptVoteRequest
    )

    @POST("api/v1/lamp-match/reject")
    suspend fun reject(
        @Body rejectVoteRequest: RejectVoteRequest
    )

    @GET("api/v1/lamp-match")
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
