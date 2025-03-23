package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface LampMatchService {
    @POST("api/v1/lamp-match/start")
    suspend fun startMatch()

    @POST("api/v1/lamp-match/stop")
    suspend fun stopMatch()

    @GET("api/v1/lamp-match")
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
