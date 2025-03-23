package com.devndev.lamp.data.datsource.lampmatach

import com.devndev.lamp.data.dto.response.lampmatch.MatchSuggestionResponse

interface LampMatchDataSource {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun getMatchSuggestion(): MatchSuggestionResponse
}
