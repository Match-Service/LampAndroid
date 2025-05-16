package com.devndev.lamp.data.datsource.lampmatach

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

    override suspend fun accept(lampSuggestionId: Int) {
        lampMatchService.accept(
            lampSuggestionId = lampSuggestionId
        )
    }

    override suspend fun reject(lampSuggestionId: Int) {
        lampMatchService.reject(
            lampSuggestionId = lampSuggestionId
        )
    }

    override suspend fun getMatchSuggestion(): MatchSuggestionResponse {
        return lampMatchService.getMatchSuggestion()
    }
}
