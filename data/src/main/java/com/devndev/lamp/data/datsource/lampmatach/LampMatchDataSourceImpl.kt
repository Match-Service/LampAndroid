package com.devndev.lamp.data.datsource.lampmatach

import com.devndev.lamp.data.dto.request.lamp.VoteAcceptRequest
import com.devndev.lamp.data.dto.request.lamp.VoteRejectRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
import com.devndev.lamp.data.service.LampMatchService
import retrofit2.Response
import javax.inject.Inject

class LampMatchDataSourceImpl @Inject constructor(
    private val lampMatchService: LampMatchService
) : LampMatchDataSource {
    override suspend fun startMatch() {
        lampMatchService.startMatch()
    }

    override suspend fun stopMatch() {
        lampMatchService.stopMatch()
    }

    override suspend fun accept(voteAcceptRequest: VoteAcceptRequest): Response<Unit> {
        return lampMatchService.accept(voteAcceptRequest)
    }

    override suspend fun reject(voteRejectRequest: VoteRejectRequest): Response<Unit> {
        return lampMatchService.reject(voteRejectRequest)
    }

    override suspend fun getMatchSuggestion(): MatchSuggestionResponse {
        return lampMatchService.getMatchSuggestion()
    }
}
