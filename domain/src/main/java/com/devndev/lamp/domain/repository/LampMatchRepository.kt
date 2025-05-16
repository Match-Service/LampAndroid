package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel

interface LampMatchRepository {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun accept(lampSuggestionId: Int)
    suspend fun reject(lampSuggestionId: Int)
    suspend fun getMatchSuggestion(): MatchSuggestionDomainModel
}
