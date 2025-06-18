package com.devndev.lamp.data.datasource.lampmatch

import com.devndev.lamp.data.dto.request.lampmatch.AcceptVoteRequest
import com.devndev.lamp.data.dto.request.lampmatch.RejectVoteRequest
import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse
import com.devndev.lamp.data.service.LampMatchService
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

    override suspend fun accept(acceptVoteRequest: AcceptVoteRequest) {
        lampMatchService.accept(
            acceptVoteRequest = acceptVoteRequest
        )
    }

    override suspend fun reject(rejectVoteRequest: RejectVoteRequest) {
        lampMatchService.reject(
            rejectVoteRequest = rejectVoteRequest
        )
    }

    override suspend fun getMatchSuggestion(): MatchSuggestionResponse {
        return lampMatchService.getMatchSuggestion()
    }
}
