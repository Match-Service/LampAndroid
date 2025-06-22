package com.devndev.lamp.data.datsource.lampmatach

import com.devndev.lamp.data.dto.request.lamp.VoteAcceptRequest
import com.devndev.lamp.data.dto.request.lamp.VoteRejectRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
import retrofit2.Response

interface LampMatchDataSource {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun accept(voteAcceptRequest: VoteAcceptRequest): Response<Unit>
    suspend fun reject(voteRejectRequest: VoteRejectRequest): Response<Unit>
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
