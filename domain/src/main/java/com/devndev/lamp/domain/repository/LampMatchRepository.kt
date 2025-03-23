package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel

interface LampMatchRepository {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun getMatchSuggestion(): MatchSuggestionDomainModel
}
