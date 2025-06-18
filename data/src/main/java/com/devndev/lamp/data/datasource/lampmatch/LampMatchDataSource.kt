package com.devndev.lamp.data.datasource.lampmatch

import com.devndev.lamp.data.dto.request.lampmatch.AcceptVoteRequest
import com.devndev.lamp.data.dto.request.lampmatch.RejectVoteRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse

interface LampMatchDataSource {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun accept(acceptVoteRequest: AcceptVoteRequest)
    suspend fun reject(rejectVoteRequest: RejectVoteRequest)
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
